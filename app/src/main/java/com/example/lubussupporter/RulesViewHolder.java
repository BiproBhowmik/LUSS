package com.example.lubussupporter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RulesViewHolder extends RecyclerView.ViewHolder {

    TextView emergencyName;

    public RulesViewHolder(@NonNull View itemView) {
        super(itemView);

        emergencyName = itemView.findViewById(R.id.rulesNameCard);
    }
}
