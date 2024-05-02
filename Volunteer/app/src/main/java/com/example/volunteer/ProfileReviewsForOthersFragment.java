package com.example.volunteer;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class ProfileReviewsForOthersFragment extends Fragment {

    String userEmail;
    List<ItemReview> items = new ArrayList<ItemReview>();
    ReviewsAdapter adapter;

    RecyclerView recyclerView;

    Button addReview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ProfileForOtherUsersActivity activity=(ProfileForOtherUsersActivity) getActivity();
        userEmail = activity.getMyData();

        String arrayName[]= new String[100];
        String arrayEmail[] = new String[100];

        return inflater.inflate(R.layout.fragment_profile_reviews_for_others, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addReview=view.findViewById(R.id.add_review_button);
        recyclerView=view.findViewById(R.id.recyclerview_reviews);
        adapter = new ReviewsAdapter(getActivity(),items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ReviewsAdapter(getActivity().getApplicationContext(),items));

        //loading posts from database
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_http.getURL()+"login-registration-android/reviews_get.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response.toString());
                try {
                    JSONArray itemsJsonArray = new JSONArray(response);
                    for(int i=0; i<itemsJsonArray.length(); i++){
                        JSONObject itemsObject = itemsJsonArray.getJSONObject(i);
                        String name = itemsObject.getString("name");
                        String email=itemsObject.getString("email");
                        String review = itemsObject.getString("review");
                        String dateTime=itemsObject.getString("dateTime");
                        String reviewId =itemsObject.getString("reviewId");

                        ItemReview item = new ItemReview(name, email,review,dateTime,reviewId);
                        items.add(item);
                    }
                    adapter = new ReviewsAdapter(getActivity(),items);
                    recyclerView.setAdapter(adapter);

                    //sorting the posts by the date, the newest on the top
                    Collections.sort(items, new Comparator<ItemReview>() {
                        @Override
                        public int compare(ItemReview o1, ItemReview o2) {
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
        })
        {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                paramV.put("email",userEmail);
                return paramV;
            }
        };
        queue.add(stringRequest);

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddReviewActivity.class);
                i.putExtra("email", userEmail);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
    }
}