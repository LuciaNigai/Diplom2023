package com.example.volunteer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class ProfileForOtherUsersFragment extends Fragment {

    String userEmail;
    TextView textViewName, textViewEmail;
    ImageView imageViewProfilePic;
    String url1=Url_http.getURL();
    LinearLayout linearLayoutPosts, linearLayoutReviews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ProfileForOtherUsersActivity activity=(ProfileForOtherUsersActivity) getActivity();
        userEmail = activity.getMyData();
        return inflater.inflate(R.layout.fragment_profile_for_other_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewName=view.findViewById(R.id.name);
        textViewEmail=view.findViewById(R.id.email);
        imageViewProfilePic=view.findViewById(R.id.imageView_profile);
        //Toast.makeText(getActivity(), userEmail, Toast.LENGTH_SHORT).show();

        //setting user name, email and photo from database
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = url1 + "login-registration-android/user_info_for_others.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray itemsJsonArray = new JSONArray(response);

                            for (int i = 0; i < itemsJsonArray.length(); i++) {
                                JSONObject itemsObject = itemsJsonArray.getJSONObject(i);
                                String name = itemsObject.getString("name");
                                String email = itemsObject.getString("email");
                                String image = itemsObject.getString("user_photo");

                                textViewName.setText(name);
                                textViewEmail.setText(email);
                                imageViewProfilePic.setImageResource(getResources().getIdentifier(image, "drawable", getActivity().getPackageName()));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("email", userEmail);
                return  paramV;
            }
        };
        queue.add(stringRequest);

        //getUser's posts
        linearLayoutPosts=view.findViewById(R.id.posts_info);
        linearLayoutPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UserPostsForOtherUsersFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container2, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        linearLayoutReviews=view.findViewById(R.id.reviews_for_others);

        linearLayoutReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileReviewsForOthersFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container2, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }
}