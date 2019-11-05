package com.example.lubussupporter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback extends AppCompatActivity implements View.OnClickListener {

    private RatingBar ratingbar;
    private Button ratingButton;
    private TextView ratingText;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //back button
        getSupportActionBar().setTitle("Rate Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ratingbar = findViewById(R.id.ratingBar);
        ratingButton = findViewById(R.id.ratingButton);
        ratingText = findViewById(R.id.ratingText);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedbacks");

        dataShow();

        ratingButton.setOnClickListener(this);

    }

    private void dataShow() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float storedRtn = 0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Feedback_StoreToDB feedback_storeToDB = dataSnapshot1.getValue(Feedback_StoreToDB.class);
                    storedRtn += feedback_storeToDB.getRating();

                }

                ratingText.setText("Average Rating : "+String.valueOf(storedRtn / dataSnapshot.getChildrenCount()) +" in 5");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        float ratingF = ratingbar.getRating();

        String uName = "", sid = "";

        //Load SharedPre
        SharedPreferences sharedPreferences = getSharedPreferences("myFileUNandEM", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("username") && sharedPreferences.contains("id")) {

            uName = sharedPreferences.getString("username", "Unknown");
            sid = sharedPreferences.getString("id", "Unknown");
        }

        databaseReference.child(sid+uName).setValue(new Feedback_StoreToDB(sid, ratingF));

        Toast.makeText(getApplicationContext(), "Thanks for Rating", Toast.LENGTH_LONG).show();
        Snackbar.make(v, "You've rated " + ratingF +" out of 5", Snackbar.LENGTH_LONG).show();
    }
}
