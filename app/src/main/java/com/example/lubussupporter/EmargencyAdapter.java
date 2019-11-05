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

public class EmargencyAdapter extends RecyclerView.Adapter<EmargencyViewHolder> {

    private Context context;
    private List<Emergency_StoreToDB> databaseList;

    //private DatabaseReference database;

    public EmargencyAdapter(Context context, List<Emergency_StoreToDB> databaseList) {
        this.context = context;
        Collections.reverse(databaseList);
        this.databaseList = databaseList;
    }

    @NonNull
    @Override
    public EmargencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.emergency_view_raw, viewGroup, false);


        return new EmargencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmargencyViewHolder questionViewerViewHolder, int i) {

        Emergency_StoreToDB storeToDB = databaseList.get(i);

        final String sEname = storeToDB.getName();
        final String sEnumber = storeToDB.getNumber();

        questionViewerViewHolder.emergencyName.setText(sEname);
        questionViewerViewHolder.emergencyNumber.setText(sEnumber);

        questionViewerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, QuestionLargeView.class);
//                intent.putExtra("username", sUsername);
//                intent.putExtra("id", sId);
//                intent.putExtra("title", stitle);
//                intent.putExtra("discription", sdiscription);
//                intent.putExtra("image", simageURI);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //why??
//                context.startActivity(intent);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+sEnumber));
                System.out.println("tell"+sEnumber);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return databaseList.size();
    }

    public void filterListn(List<Emergency_StoreToDB> filteredList)
    {
        databaseList = filteredList;
        notifyDataSetChanged();
    }
}
