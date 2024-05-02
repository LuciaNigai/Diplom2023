package com.example.volunteer;

import android.view.ContentInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyPostsViewHolder extends RecyclerView.ViewHolder {

    ImageView imageViewProfile, imageViewPost;
    TextView textViewDataTime, textViewName, textViewEmail, textViewCategory, textViewState, textViewDescription, postMenu;
    RelativeLayout relativeLayoutPost, relativeLayoutUser;

    public MyPostsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewProfile=(ImageView) itemView.findViewById(R.id.image_view_post_item);
        textViewDataTime=itemView.findViewById(R.id.item_post_datetime);
        textViewName=itemView.findViewById(R.id.post_item_name);
        textViewEmail=itemView.findViewById(R.id.post_item_email);
        textViewCategory=itemView.findViewById(R.id.item_post_category);
        textViewState=itemView.findViewById(R.id.item_post_state);
        textViewDescription=itemView.findViewById(R.id.item_post_description);
        imageViewPost=(ImageView) itemView.findViewById(R.id.image_view_post_photo);
        postMenu=itemView.findViewById(R.id.textViewOptions);
        relativeLayoutUser=itemView.findViewById(R.id.user_info);
        relativeLayoutPost = itemView.findViewById(R.id.post_layout);
    }
}
