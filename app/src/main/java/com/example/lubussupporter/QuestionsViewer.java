package com.example.lubussupporter;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class QuestionsViewer extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuestionViewerAdapter myAdapter;

    private List<Question_StoreToDB> databaseList;
    private DatabaseReference mDatabase;

    private ProgressBar mainProgressbar;
    private EditText searchEdittext;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_viewer);

        //back button
        getSupportActionBar().setTitle("All Posts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchEdittext = findViewById(R.id.searchEditTextMain);
        searchEdittext.setVisibility(View.INVISIBLE);

        mainProgressbar = findViewById(R.id.mainProgressbar);
        Sprite doubleBounce = new DoubleBounce();
        mainProgressbar.setIndeterminateDrawable(doubleBounce);
        mainProgressbar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setLayoutManager(new VegaLayoutManager());


        databaseList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("QuestionsOrPosts");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Question_StoreToDB upload = dataSnapshot1.getValue(Question_StoreToDB.class);
                    databaseList.add(upload);
                }

                myAdapter = new QuestionViewerAdapter(getApplicationContext(), databaseList);

                recyclerView.setAdapter(myAdapter);

                mainProgressbar.setVisibility(View.INVISIBLE);
                searchEdittext.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                mainProgressbar.setVisibility(View.INVISIBLE);
            }

        });

        ///For Search EditText
        searchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });


    }

    private void filter(String text) {

        List<Question_StoreToDB> filteredList = new ArrayList<>();

        for(Question_StoreToDB item : databaseList)
        {
            if(item.getUsername().toLowerCase().contains(text.toLowerCase()))
            {
                filteredList.add(item);
            }
        }

        myAdapter.filterListn(filteredList);
    }
}
