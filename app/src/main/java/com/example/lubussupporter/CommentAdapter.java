package com.example.lubussupporter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private Context context;
    private List<Comment_StoreToDB> databaseList;

    //private DatabaseReference database;

    public CommentAdapter(Context context, List<Comment_StoreToDB> databaseList) {
        this.context = context;
        this.databaseList = databaseList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.comment_view_raw, viewGroup, false);


        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder questionViewerViewHolder, int i) {

        Comment_StoreToDB storeToDB = databaseList.get(i);

        final String sUsername = storeToDB.getUsername();
        final String sId = storeToDB.getStudentID();
        final String sComment = storeToDB.getComment();
        final String sDate = storeToDB.getDate();
        final String sTime = storeToDB.getTime();

        questionViewerViewHolder.commentUsername.setText(sUsername);
        questionViewerViewHolder.commentId.setText(sId);
        questionViewerViewHolder.show_comment.setText(sComment);
        questionViewerViewHolder.commentDate.setText(sDate);
        questionViewerViewHolder.commentTime.setText(sTime);

    }

    @Override
    public int getItemCount() {
        return databaseList.size();
    }
}
