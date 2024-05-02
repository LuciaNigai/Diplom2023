package com.example.volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostCommentsActivity extends AppCompatActivity {

    List<ItemComment> items = new ArrayList<ItemComment>();
    MyPostCommentAdapterClass adapter;

    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    String postId;

    Button addComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comments);

        sharedPreferences = PostCommentsActivity.this.getSharedPreferences("MyAppName", Context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(PostCommentsActivity.this.getApplicationContext());


        View view;
        recyclerView=findViewById(R.id.recyclerview_comments);
        adapter = new MyPostCommentAdapterClass(this,items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyPostCommentAdapterClass(this.getApplicationContext(),items));

        postId= getIntent().getStringExtra("postId");
        Toast.makeText(PostCommentsActivity.this, postId, Toast.LENGTH_SHORT).show();

        //loading posts from database
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_http.getURL()+"login-registration-android/comments_get.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.toString());
                try {
                    JSONArray itemsJsonArray = new JSONArray(response);

                    for(int i=0; i<itemsJsonArray.length(); i++){
                        JSONObject itemsObject = itemsJsonArray.getJSONObject(i);
                        String name = itemsObject.getString("name");
                        String email= itemsObject.getString("email");
                        String comment = itemsObject.getString("comment");
                        String dateTime=itemsObject.getString("dateTime");
                        String commentId =itemsObject.getString("comment_id");

                        ItemComment item = new ItemComment(name, email, comment,dateTime, commentId);
                        items.add(item);
                    }
                    adapter = new MyPostCommentAdapterClass(PostCommentsActivity.this,items);
                    recyclerView.setAdapter(adapter);

                    //sorting the posts by the date, the newest on the top
                    Collections.sort(items, new Comparator<ItemComment>() {
                        @Override
                        public int compare(ItemComment o1, ItemComment o2) {
                            return o2.getDateTime().compareTo(o1.dateTime);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostCommentsActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
        protected Map<String, String> getParams() {
            Map<String, String> paramV = new HashMap<>();
            paramV.put("post_id",postId);
            return paramV;
        }
    };
        Volley.newRequestQueue(this).add(stringRequest);

        addComment=findViewById(R.id.add_comment_button);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddComment = new Intent(PostCommentsActivity.this,AddCommentActivity.class);
                intentAddComment.putExtra("postId", postId);
                startActivity(intentAddComment);
            }
        });

    }
}