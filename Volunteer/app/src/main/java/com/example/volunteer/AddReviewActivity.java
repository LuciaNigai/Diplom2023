package com.example.volunteer;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

import java.sql.Array;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AddReviewActivity extends AppCompatActivity {

    String userEmail, reviewId;
    String url = Url_http.getURL();

    TextInputEditText textInputEditTextAddReview;
    Button buttonAddReviewOK, buttonAddReviewCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        userEmail=getIntent().getStringExtra("email");
        reviewId=getIntent().getStringExtra("reviewId");
        textInputEditTextAddReview =(TextInputEditText) findViewById(R.id.add_review);
        buttonAddReviewOK=findViewById(R.id.add_review_OK);
        buttonAddReviewCancel=findViewById(R.id.add_review_cancel);

   //    Toast.makeText(AddReviewActivity.this, reviewId, Toast.LENGTH_SHORT).show();

        if (!TextUtils.isEmpty(reviewId)) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "login-registration-android/get_review_to_edit.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (response != null && response.length() > 0)
                        {
                            JSONArray itemsJsonArray = new JSONArray(response);
                            for (int i = 0; i < itemsJsonArray.length(); i++) {
                                JSONObject itemsObject = itemsJsonArray.getJSONObject(i);
                                String review = itemsObject.getString("review");
                                textInputEditTextAddReview.setText(review);
                                Toast.makeText(AddReviewActivity.this, "Change review", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(response.isEmpty()) {
                            Toast.makeText(AddReviewActivity.this, "You can't change someone else's review", Toast.LENGTH_SHORT).show();
                            Intent goback = new Intent(AddReviewActivity.this, MainActivity.class);
                            startActivity(goback);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddReviewActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("review_id", reviewId);
                    return paramV;
                }
            };
            Volley.newRequestQueue(AddReviewActivity.this).add(stringRequest);
        }


            buttonAddReviewOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(reviewId!=null) {
                        String reviewContent = textInputEditTextAddReview.getText().toString();
                        buttonAddReviewOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RequestQueue queue2 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url + "login-registration-android/review_change.php",
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if (response.equals("success")) {
                                                    Intent uploadPostIntent = new Intent(AddReviewActivity.this, MainActivity.class);
                                                    //if want to go to comments page
                                                    startActivity(uploadPostIntent);
                                                    Toast.makeText(getApplicationContext(), "Review changed", Toast.LENGTH_SHORT).show();
                                                } else
                                                    Toast.makeText(getApplicationContext(), "Can't change this review", Toast.LENGTH_SHORT).show();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    protected Map<String, String> getParams() {
                                        Map<String, String> paramV = new HashMap<>();
                                        paramV.put("review", reviewContent);
                                        paramV.put("review_id", reviewId);
                                        return paramV;
                                    }
                                };
                                queue2.add(stringRequest2);
                            }
                        });
                    }
                    else {
                        String reviewContent = String.valueOf(textInputEditTextAddReview.getText());
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        String url1 = url + "login-registration-android/review_add.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("success")) {
                                            Intent uploadPostIntent = new Intent(AddReviewActivity.this, MainActivity.class);
                                            //if want to go to comments page
                                            startActivity(uploadPostIntent);
                                            Toast.makeText(getApplicationContext(), "Review Uploaded", Toast.LENGTH_SHORT).show();
                                        } else
                                            Toast.makeText(getApplicationContext(), "Can't add review to yourself", Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            protected Map<String, String> getParams() {
                                Map<String, String> paramV = new HashMap<>();
                                paramV.put("review", reviewContent);
                                paramV.put("email", userEmail);
                                return paramV;
                            }
                        };
                        queue.add(stringRequest);
                    }
                }
            });

        buttonAddReviewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(AddReviewActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });
    }
}