package com.example.lubussupporter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class NoticeViewAdapter extends RecyclerView.Adapter<QuestionViewerViewHolder> {

    private Context context;
    private List<Question_StoreToDB> databaseList;

    //private DatabaseReference database;

    public NoticeViewAdapter(Context context, List<Question_StoreToDB> databaseList) {
        this.context = context;
        Collections.reverse(databaseList);
        this.databaseList = databaseList;
    }

    @NonNull
    @Override
    public QuestionViewerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.question_viewer_raw, viewGroup, false);


        return new QuestionViewerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionViewerViewHolder questionViewerViewHolder, int i) {

        Question_StoreToDB storeToDB = databaseList.get(i);

        final String stitle = storeToDB.getTitle();
        final String sdiscription = storeToDB.getDiscription();
        final String sUsername = storeToDB.getUsername();
        final String sId = storeToDB.getId();
        final String sDate = storeToDB.getDate();
        final String sTime = storeToDB.getTime();
        final String simageURI = storeToDB.getImageUrl();
        final String sKey = storeToDB.getKey();

        questionViewerViewHolder.title.setText(stitle);
        questionViewerViewHolder.description.setText(sdiscription);
        questionViewerViewHolder.username.setText(sUsername);
        questionViewerViewHolder.id.setText(sId);
        questionViewerViewHolder.date.setText(sDate);
        questionViewerViewHolder.time.setText(sTime);

        Picasso.with(context)
                .load(simageURI)
                .placeholder(R.drawable.app_logo)
                .fit()
                .centerCrop()
                .into(questionViewerViewHolder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        questionViewerViewHolder.mainProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        questionViewerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoticeLargeView.class);
                intent.putExtra("username", sUsername);
                intent.putExtra("id", sId);
                intent.putExtra("title", stitle);
                intent.putExtra("discription", sdiscription);
                intent.putExtra("image", simageURI);
                intent.putExtra("key", sKey);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //why??
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return databaseList.size();
    }

    public void filterListn(List<Question_StoreToDB> filteredList)
    {
        databaseList = filteredList;
        notifyDataSetChanged();
    }
}
