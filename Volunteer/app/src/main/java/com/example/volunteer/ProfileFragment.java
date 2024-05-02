package com.example.volunteer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class ProfileFragment extends Fragment {

    LinearLayout postsLayout, profileInfo, profileReviews;
    String url =Url_http.getURL();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //logout part
        TextView textViewName, textViewEmail;
        ImageView imageViewProfilePic;
        Button buttonLogout;


        //logout part end

        // logout start
        textViewName = getActivity().findViewById(R.id.name);
        textViewEmail = getActivity().findViewById(R.id.email);
        buttonLogout = getActivity().findViewById(R.id.logout);
        imageViewProfilePic=getActivity().findViewById(R.id.imageView_profile);

        //for posts----------------------info about the users posts--------------------------------------
        postsLayout =view.findViewById(R.id.posts_info);
        postsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfilePostsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        //-----------------------info about the users posts end--------------------------

        // profile info begin

        profileInfo=view.findViewById(R.id.profile_personal_info);
        profileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PersonalInformationFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //profile info end


        //logout part start
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MyAppName", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("logged", "false").equals("false")) {
            Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
            startActivity(intent);
            getActivity().finish();
        }


        Toast.makeText(getActivity(),sharedPreferences.getString("email",""),Toast.LENGTH_SHORT).show();

        //setting user name, email and photo from database
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+"login-registration-android/get_user_info.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray itemsJsonArray = new JSONArray(response);

                    for(int i=0; i<itemsJsonArray.length(); i++){
                        JSONObject itemsObject = itemsJsonArray.getJSONObject(i);
                        String name = itemsObject.getString("name");
                        String email= itemsObject.getString("email");
                        String image=itemsObject.getString("user_photo");


                        textViewName.setText(name);
                        textViewEmail.setText(email);
                        imageViewProfilePic.setImageResource(getResources().getIdentifier(image, "drawable", getActivity().getPackageName()));
                    }

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



        //popup window logout confirmation
        AlertDialog.Builder builder;
        builder= new AlertDialog.Builder(getActivity());

        //logout button
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //popup window logout confirmation
                builder.setTitle("Logout confirmation");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //logout begin
                        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                        String url = Url_http.getURL()+"login-registration-android/logout.php";

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                response -> {

                                    if(response.equals("success")) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("logged", "");
                                        editor.putString("name", "");
                                        editor.putString("email", "");
                                        editor.putString("apiKey", "");
                                        editor.apply();
                                        Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                    else
                                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                }, Throwable::printStackTrace)
                        {
                            protected Map<String, String> getParams() {
                                Map<String, String> paramV = new HashMap<>();
                                paramV.put("email", textViewEmail.getText().toString());
                                return paramV;
                            }
                        };
                        queue.add(stringRequest);
                        //logout end
                    }
                });

                //logout popup window
                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                //logout popup window

                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Logout confirmation");
                alert.show();
            }
        });

        //logout part end

        profileReviews=view.findViewById(R.id.reviews);
        profileReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReviews = new Intent(getActivity(),ProfileForOtherUsersActivity.class);
                intentReviews.putExtra("userEmail", sharedPreferences.getString("email",""));
                intentReviews.putExtra("myprofile","aaaa");
                startActivity(intentReviews);
            }
        });
    }
}