package com.example.lubussupporter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

public class RulesAdapter extends RecyclerView.Adapter<RulesViewHolder> {

    private Context context;
    private List<Rules_StoreToDB> databaseList;

    //private DatabaseReference database;

    public RulesAdapter(Context context, List<Rules_StoreToDB> databaseList) {
        this.context = context;
        this.databaseList = databaseList;
    }

    @NonNull
    @Override
    public RulesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rules_raw, viewGroup, false);


        return new RulesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RulesViewHolder questionViewerViewHolder, int i) {

        Rules_StoreToDB storeToDB = databaseList.get(i);

        final String sEname = storeToDB.getRule();

        questionViewerViewHolder.emergencyName.setText(sEname);

    }

    @Override
    public int getItemCount() {
        return databaseList.size();
    }
}
