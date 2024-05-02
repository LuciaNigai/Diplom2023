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

public class MyPostCommentAdapterClass  extends RecyclerView.Adapter<MyPostCommentViewHolder>  {

    Context context;
    List<ItemComment> items;
    String url1 = Url_http.getURL();

    public MyPostCommentAdapterClass(Context context, List<ItemComment> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyPostCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPostCommentViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_view_post,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostCommentViewHolder holder, int position) {
        ItemComment item = items.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewEmail.setText(item.getEmail());
        holder.textViewComment.setText(item.getComment());

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
                                builder.setTitle("Comment removal");
                                builder.setMessage("Are you sure you want to delete this comment?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String commentId = items.get(holder.getAdapterPosition()).getCommentId();
                                        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                                        String url = url1 + "login-registration-android/delete_comment.php";
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (response.equals("success")) {
                                                            Intent uploadPostIntent = new Intent(context, MainActivity.class);
                                                            context.startActivity(uploadPostIntent);
                                                            Toast.makeText(context.getApplicationContext(), "Comment deleted", Toast.LENGTH_SHORT).show();
                                                        } else
                                                            Toast.makeText(context.getApplicationContext(), "You can't delete someone else's comment", Toast.LENGTH_SHORT).show();
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(context.getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }) {
                                            protected Map<String, String> getParams() {
                                                Map<String, String> paramV = new HashMap<>();
                                                paramV.put("comment_id", commentId);
                                                return paramV;
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
                                alert.setTitle("Comment removal confirmation");
                                alert.show();

                                return true;
                            case R.id.menu2:

                                AlertDialog.Builder builder2;
                                builder2= new AlertDialog.Builder(context);
                                builder2.setTitle("Comment change");
                                builder2.setMessage("Are you sure you want to change this comment?");
                                builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                Intent intentChangePost = new Intent(context, AddCommentActivity.class);
                                //gets the item's post id
                                intentChangePost.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intentChangePost.putExtra("commentId", items.get(holder.getAdapterPosition()).getCommentId());
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
                                alert2.setTitle("Comment change confirmation");
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
