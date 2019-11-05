package com.example.lubussupporter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

public class QuestionViewerViewHolder extends RecyclerView.ViewHolder {

    TextView title, description, username, id, loveCounter, date, time;
    ImageView imageView;
    ProgressBar mainProgressbar;

    public QuestionViewerViewHolder(@NonNull View itemView) {
        super(itemView);

        mainProgressbar = itemView.findViewById(R.id.qMainProgressbar);
        Sprite doubleBounce = new DoubleBounce();
        mainProgressbar.setIndeterminateDrawable(doubleBounce);
        mainProgressbar.setVisibility(View.VISIBLE);

        title = itemView.findViewById(R.id.cardTitle);
        description = itemView.findViewById(R.id.cardDiscription);
        username = itemView.findViewById(R.id.cardUsername);
        id = itemView.findViewById(R.id.cardId);
        date = itemView.findViewById(R.id.cardDate);
        time = itemView.findViewById(R.id.cardTime);
        imageView = itemView.findViewById(R.id.cardImageView);

    }
}
