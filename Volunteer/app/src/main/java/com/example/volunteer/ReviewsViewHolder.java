package com.example.volunteer;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsViewHolder extends RecyclerView.ViewHolder{
    TextView textViewName, textViewEmail, textViewReview, textButtonMenu;
    RelativeLayout postCommentLayout;

    public ReviewsViewHolder(@NonNull View itemView){
        super(itemView);
        textViewName=itemView.findViewById(R.id.post_comment_name);
        textViewEmail=itemView.findViewById(R.id.post_comment_email);
        textViewReview =itemView.findViewById(R.id.item_post_comment);
        textButtonMenu=itemView.findViewById(R.id.commentMenu);
        postCommentLayout=itemView.findViewById(R.id.comment_layout);
    }
}
