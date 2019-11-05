package com.example.lubussupporter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class GrroupMessageViewHolder extends RecyclerView.ViewHolder {

    public TextView username,show_message, messageId, messageTime, messageDate;

    public GrroupMessageViewHolder(@NonNull View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.messageUsername);
        show_message = itemView.findViewById(R.id.messageMessage);
        messageId = itemView.findViewById(R.id.messageID);
        messageDate = itemView.findViewById(R.id.messageDate);
        messageTime = itemView.findViewById(R.id.messageTime);
    }
}
