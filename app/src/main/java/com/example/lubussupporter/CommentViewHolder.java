package com.example.lubussupporter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CommentViewHolder extends RecyclerView.ViewHolder {

    public TextView commentUsername,show_comment, commentId, commentTime, commentDate;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);

        commentUsername = itemView.findViewById(R.id.commentUsername);
        show_comment = itemView.findViewById(R.id.commentComment);
        commentId = itemView.findViewById(R.id.commentID);
        commentDate = itemView.findViewById(R.id.commentDate);
        commentTime = itemView.findViewById(R.id.commentTime);
    }
}
