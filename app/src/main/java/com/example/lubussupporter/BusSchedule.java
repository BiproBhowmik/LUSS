package com.example.lubussupporter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class BusSchedule extends AppCompatActivity implements View.OnClickListener {

    private CardView regular, exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule);

        //back button
        getSupportActionBar().setTitle("Bus Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        regular = findViewById(R.id.busScheduleRegular);
        exam = findViewById(R.id.busScheduleExam);


        regular.setOnClickListener(this);
        exam.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.busScheduleRegular)
        {
            startActivity(new Intent(BusSchedule.this, BusScheduleRegular.class));
        } else if (v.getId() == R.id.busScheduleExam)
        {
            startActivity(new Intent(BusSchedule.this, BusScheduleExam.class));
        }
    }
}
