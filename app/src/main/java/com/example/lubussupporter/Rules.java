package com.example.lubussupporter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class Rules extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RulesAdapter myAdapter;

    private List<Rules_StoreToDB> databaseList;
    private DatabaseReference mDatabase;

    private ProgressBar mainProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        //back button
        getSupportActionBar().setTitle("Rules");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainProgressbar = findViewById(R.id.rulesMainProgressbar);
        Sprite doubleBounce = new DoubleBounce();
        mainProgressbar.setIndeterminateDrawable(doubleBounce);
        mainProgressbar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.rulesRecyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setLayoutManager(new VegaLayoutManager());


        databaseList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Rules");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Rules_StoreToDB upload = dataSnapshot1.getValue(Rules_StoreToDB.class);
                    databaseList.add(upload);
                }

                myAdapter = new RulesAdapter(getApplicationContext(), databaseList);

                recyclerView.setAdapter(myAdapter);

                mainProgressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                mainProgressbar.setVisibility(View.INVISIBLE);
            }

        });

    }
}
