package com.example.volunteer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
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
import java.util.List;


public class HomeFragment extends Fragment {
//recycle view
    List<Item> items = new ArrayList<Item>();
    MyPostAdapterCLass adapter;

    RecyclerView recyclerView;
    String url= Url_http.getURL();
    //recycle view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //posts entering information
        recyclerView = view.findViewById(R.id.recycler_view_posts);

        ///--------------------------------get data from the database------------------------------------------------------
        adapter = new MyPostAdapterCLass(getActivity(),items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyPostAdapterCLass(getActivity().getApplicationContext(),items));
       //loading posts from database
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+"login-registration-android/post_get.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray itemsJsonArray = new JSONArray(response);

                    for(int i=0; i<itemsJsonArray.length(); i++){
                        JSONObject itemsObject = itemsJsonArray.getJSONObject(i);
                        String name = itemsObject.getString("name");
                        String email= itemsObject.getString("email");
                        String image=itemsObject.getString("user_photo");
                        String category = itemsObject.getString("post_category");
                        String state = itemsObject.getString("post_state");
                        String postDescription = itemsObject.getString("post_description");
                        String  postImage = itemsObject.getString("post_photo");
                        String dateTime = itemsObject.getString("post_date");
                        String postId= itemsObject.getString("post_id");

                        Item item = new Item(name, email, image, category, state, postDescription, postImage, dateTime, postId);
                        items.add(item);
                    }
                    adapter = new MyPostAdapterCLass(getActivity(),items);
                    recyclerView.setAdapter(adapter);

                    //sorting the posts by the date, the newest on the top
                    Collections.sort(items, new Comparator<Item>() {
                        @Override
                        public int compare(Item o1, Item o2) {
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
                Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);



        //---------------------------------------end-------------------------------------
        //post logic end

        //button add post

        Button buttonAddPost;
        buttonAddPost = view.findViewById(R.id.add_post_button);
        buttonAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentButton = new Intent(getActivity(),AddEditPost.class);
                startActivity(intentButton);
                ((Activity)getActivity()).overridePendingTransition(0,0);
            }
        });
        //end
    }

    //end
}