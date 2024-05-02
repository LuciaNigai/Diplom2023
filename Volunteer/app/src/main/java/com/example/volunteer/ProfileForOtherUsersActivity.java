package com.example.volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class ProfileForOtherUsersActivity extends AppCompatActivity {

    String userEmail, myReviews;
    String url1=Url_http.getURL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_for_other_users);

        userEmail = getIntent().getStringExtra("userEmail");
       // Toast.makeText(ProfileForOtherUsersActivity.this, userEmail, Toast.LENGTH_SHORT).show();

        ///////////
        myReviews = getIntent().getStringExtra("myprofile");
        if (myReviews != null) {
            Fragment fragment = new ProfileReviewsForOthersFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container2, fragment);
            fragmentTransaction.commit();
        }
        else {

            ////////////


            //setting user name, email and photo from database
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
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

                                    userEmail = email;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("email", userEmail);
                    return paramV;
                }
            };
            queue.add(stringRequest);

            Fragment fragment = new ProfileForOtherUsersFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container2, fragment);
            fragmentTransaction.commit();
        }
    }

    public String getMyData() {
        return userEmail;
    }

}