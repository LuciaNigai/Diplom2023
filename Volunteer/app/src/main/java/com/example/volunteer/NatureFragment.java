package com.example.volunteer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public  class NatureFragment extends Fragment {

    List<Item> items_nature = new ArrayList<Item>();
    MyPostAdapterCLass adapter_nature;
    RecyclerView recyclerView_nature;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nature, container, false);
    }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);

         //posts entering information
         recyclerView_nature = view.findViewById(R.id.recycler_view_posts_nature);

         ///--------------------------------get data from the database------------------------------------------------------
         adapter_nature = new MyPostAdapterCLass(getActivity(), items_nature);
         recyclerView_nature.setLayoutManager(new LinearLayoutManager(getActivity()));
         recyclerView_nature.setAdapter(new MyPostAdapterCLass(getActivity().getApplicationContext(), items_nature));
         loadProducts();



         //---------------------------------------end-------------------------------------
         //post logic end

         //button add post
         Button buttonAddPost;
         buttonAddPost = view.findViewById(R.id.add_post_button_nature);
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

    //reading post data from the database
    private void loadProducts(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url_http.getURL()+"login-registration-android/post_nature_get.php", new Response.Listener<String>() {
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
                        String postImage = itemsObject.getString("post_photo");
                        String dateTime = itemsObject.getString("post_date");
                        String postId= itemsObject.getString("post_id");

                        //String imagePost = "C:\\xampp\\htdocs\\login-registration-android\\imagesPosts\\"+postImage;
                        Item item = new Item(name, email, image, category, state, postDescription, postImage, dateTime, postId);
                        items_nature.add(item);
                    }
                    adapter_nature = new MyPostAdapterCLass(getActivity(), items_nature);
                    recyclerView_nature.setAdapter(adapter_nature);

                    //sorting the posts by the date, the newest on the top
                    Collections.sort(items_nature, new Comparator<Item>() {
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
    }

 }