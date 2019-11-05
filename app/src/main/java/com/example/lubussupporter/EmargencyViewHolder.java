package com.example.lubussupporter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class EmargencyViewHolder extends RecyclerView.ViewHolder {

    TextView emergencyName, emergencyNumber;

    public EmargencyViewHolder(@NonNull View itemView) {
        super(itemView);

        emergencyName = itemView.findViewById(R.id.emargencyNameCard);
        emergencyNumber = itemView.findViewById(R.id.emargencyNumberCard);
    }
}
