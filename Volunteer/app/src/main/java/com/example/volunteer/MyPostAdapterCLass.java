package com.example.volunteer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MyPostAdapterCLass extends RecyclerView.Adapter<MyPostsViewHolder> {


    Context context;
    List<Item> items;
    String url1 = Url_http.getURL();

    public MyPostAdapterCLass(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyPostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPostsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostsViewHolder holder, int position) {
        Item item = items.get(position);
        String personImagePath = item.getImage();
        holder.imageViewProfile.setImageResource(context.getResources().getIdentifier(personImagePath, "drawable", context.getPackageName()));
        holder.textViewDataTime.setText(item.getDateTime());
        holder.textViewName.setText(item.getName());
        holder.textViewEmail.setText(item.getEmail());
        holder.textViewCategory.setText(item.getCategory());
        holder.textViewState.setText(item.getState());
        holder.textViewDescription.setText(item.getDescription());
        String imagePath= item.getPostImage();
        holder.imageViewPost.setImageResource(context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName()));

        String postId= items.get(position).getPost_id();
     //   Toast.makeText(context, postId, Toast.LENGTH_SHORT).show();

        holder.postMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.postMenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.options_menu);

                //popup window
                AlertDialog.Builder builder;
                builder= new AlertDialog.Builder(context);
                //----------

                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu1:
                                builder.setTitle("Post removal");
                                builder.setMessage("Are you sure you want to delete this post?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
                                        String url = url1 + "login-registration-android/delete_post.php";
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if(response.equals("success")){
                                                            Intent uploadPostIntent = new Intent(context, MainActivity.class);
                                                            context.startActivity(uploadPostIntent);
                                                            Toast.makeText(context.getApplicationContext(), "Post deleted", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else Toast.makeText(context.getApplicationContext(), "You can't delete someone else's post", Toast.LENGTH_SHORT).show();
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(context.getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }) {
                                            protected Map<String, String> getParams() {
                                                Map<String, String> paramV = new HashMap<>();
                                                paramV.put("post_id",postId);
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
                                alert.setTitle("Post removal confirmation");
                                alert.show();
                                return true;
                            case R.id.menu2:
                                Intent intentChangePost = new Intent(context,AddEditPost.class);
                                //gets the item's post id
                                intentChangePost.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intentChangePost.putExtra("postId", items.get(holder.getAdapterPosition()).getPost_id());
                                context.startActivity(intentChangePost);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });
    //layout Post logic

        //post click listener opens the edit post part
        holder.relativeLayoutPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPostComments = new Intent(context,PostCommentsActivity.class);
                //gets the item's post id
                intentPostComments.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentPostComments.putExtra("postId", items.get(holder.getAdapterPosition()).getPost_id());
                context.startActivity(intentPostComments);
              // Toast.makeText(context.getApplicationContext(), items.get(holder.getAdapterPosition()).getPost_id(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.relativeLayoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail= item.getEmail();
                Intent intentProfileForOtherUsers = new Intent(context,ProfileForOtherUsersActivity.class);
                //gets the item's post id
                intentProfileForOtherUsers.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentProfileForOtherUsers.putExtra("userEmail", items.get(holder.getAdapterPosition()).getEmail());
                context.startActivity(intentProfileForOtherUsers);
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

}