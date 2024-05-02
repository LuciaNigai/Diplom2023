package com.example.volunteer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {
        Context context;
        List<ItemReview> items;
        String url1 = Url_http.getURL();

    public ReviewsAdapter(Context context, List<ItemReview> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewsViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_view_post,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        ItemReview item = items.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewEmail.setText(item.getEmail());
        holder.textViewReview.setText(item.getReview());


        holder.textButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu commentMenu = new PopupMenu(context, holder.textButtonMenu);
                commentMenu.inflate(R.menu.options_menu);
                commentMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                AlertDialog.Builder builder;
                                builder= new AlertDialog.Builder(context);
                                builder.setTitle("Review removal");
                                builder.setMessage("Are you sure you want to delete this review?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                String reviewId= items.get(holder.getAdapterPosition()).getReviewId();
                                RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                                String url = url1 + "login-registration-android/delete_review.php";
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                if(response.equals("success")){
                                                    Intent uploadPostIntent = new Intent(context, MainActivity.class);
                                                    context.startActivity(uploadPostIntent);
                                                    Toast.makeText(context.getApplicationContext(), "Review deleted", Toast.LENGTH_SHORT).show();
                                                }
                                                else Toast.makeText(context.getApplicationContext(), "You can't delete someone else's review", Toast.LENGTH_SHORT).show();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context.getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    protected Map<String, String> getParams() {
                                        Map<String, String> paramV = new HashMap<>();
                                        paramV.put("review_id",reviewId);
                                        return  paramV;
                                    }
                                };
                                queue.add(stringRequest);
                                    }
                                });
                                //delete post popup window
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                //delete post popup window

                                AlertDialog alert = builder.create();
                                //Setting the title manually
                                alert.setTitle("Review removal confirmation");
                                alert.show();
                                return true;
                            case R.id.menu2:
                                AlertDialog.Builder builder2;
                                builder2= new AlertDialog.Builder(context);
                                builder2.setTitle("Review change");
                                builder2.setMessage("Are you sure you want to change this review?");
                                builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intentChangePost = new Intent(context, AddReviewActivity.class);
                                        //gets the item's post id
                                        intentChangePost.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intentChangePost.putExtra("reviewId", items.get(holder.getAdapterPosition()).getReviewId());
                                        intentChangePost.putExtra("email", items.get(holder.getAdapterPosition()).getEmail());
                                        context.startActivity(intentChangePost);
                                    }
                                    });
                                    //delete post popup window
                                builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    //delete post popup window

                                    AlertDialog alert2 = builder2.create();
                                    //Setting the title manually
                                alert2.setTitle("Review change confirmation");
                                alert2.show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                commentMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    }
