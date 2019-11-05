package com.example.lubussupporter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GroupMessageAdapter extends RecyclerView.Adapter<GrroupMessageViewHolder> {

    private Context context;
    private List<GroupMessageStore> chats;

    public GroupMessageAdapter(Context context, List<GroupMessageStore> chats) {
        this.context = context;
        this.chats = chats;
    }


    @NonNull
    @Override
    public GrroupMessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.group_message_raw, viewGroup, false);
        return new GrroupMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrroupMessageViewHolder messageViewHolder, int i) {
        GroupMessageStore messageStore = chats.get(i);

        messageViewHolder.username.setText(messageStore.getUserName());
        messageViewHolder.messageId.setText(messageStore.getId());
        messageViewHolder.show_message.setText(messageStore.getMessageS());
        messageViewHolder.messageTime.setText(messageStore.getTime());
        messageViewHolder.messageDate.setText(messageStore.getDate());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
