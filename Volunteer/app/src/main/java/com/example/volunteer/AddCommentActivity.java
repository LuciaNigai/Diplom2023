package com.example.volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCommentActivity extends AppCompatActivity {

    String postId;
    TextInputEditText comment;
    String url1 = Url_http.getURL();
    String commentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        comment = (TextInputEditText) findViewById(R.id.add_comment);

        commentId = getIntent().getStringExtra("commentId");
        postId= getIntent().getStringExtra("postId");

        if (!TextUtils.isEmpty(commentId)){
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url1 + "login-registration-android/comment_update_get.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                       if (response != null && response.length() > 0)
                        {
                            JSONArray itemsJsonArray = new JSONArray(response);
                            for (int i = 0; i < itemsJsonArray.length(); i++) {
                                JSONObject itemsObject = itemsJsonArray.getJSONObject(i);
                                String commentBD = itemsObject.getString("comment");
                                String commentIdBD = itemsObject.getString("comment_id");
                                comment.setText(commentBD);
                                Toast.makeText(AddCommentActivity.this, "Change comment", Toast.LENGTH_SHORT).show();
                            }
                        }
                       else if(response.isEmpty()) {
                           Toast.makeText(AddCommentActivity.this, "You can't change someone else's comment", Toast.LENGTH_SHORT).show();
                           Intent goback = new Intent(AddCommentActivity.this, MainActivity.class);
                           startActivity(goback);
                       }
                       } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddCommentActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("comment_id", commentId);
                    return paramV;
                }
            };
            Volley.newRequestQueue(AddCommentActivity.this).add(stringRequest);
        }




        Button buttonCancelComment;
        buttonCancelComment = findViewById(R.id.add_comment_cancel);
        buttonCancelComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instead of main activity there can be the fragment home but the speed will be slower and the app can crash
                Intent cancelComment = new Intent(AddCommentActivity.this, MainActivity.class);
                startActivity(cancelComment);
            }
        });


        Button buttonAddComment;
        buttonAddComment=findViewById(R.id.add_comment_OK);
        buttonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentId!=null)
                {
                    String commentContent =String.valueOf(comment.getText());
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = url1 + "login-registration-android/comment_update.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success")){
                                        Intent uploadPostIntent = new Intent(AddCommentActivity.this, MainActivity.class);
                                        //if want to go to comments page
                                        startActivity(uploadPostIntent);
                                        Toast.makeText(getApplicationContext(), "Comment Changed", Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(getApplicationContext(), "Bad bad bad", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("comment_id", commentId);
                            paramV.put("comment",commentContent);
                            return  paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
                else
                {
                    String commentContent =String.valueOf(comment.getText());
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = url1 + "login-registration-android/comment_add.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success")){
                                        Intent uploadPostIntent = new Intent(AddCommentActivity.this, MainActivity.class);
                                        //if want to go to comments page
                                        startActivity(uploadPostIntent);
                                        Toast.makeText(getApplicationContext(), "Comment Uploaded", Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(getApplicationContext(), "Bad bad bad", Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("post_id",postId);
                            paramV.put("comment",commentContent);
                            return  paramV;
                        }
                    };
                    queue.add(stringRequest);
                }

            }
        });

    }

}