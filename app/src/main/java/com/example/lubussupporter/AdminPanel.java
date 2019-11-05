package com.example.lubussupporter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener {

    private CardView adminPass, viewComplains, addNotice, addEM, addRules, addRBS, addEXBS, validID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        //back button
        getSupportActionBar().setTitle("Admin Panel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adminPass = findViewById(R.id.adminPassword);
        viewComplains = findViewById(R.id.viewComplains);
        addNotice = findViewById(R.id.addNotice);
        addEM = findViewById(R.id.addEM);
        addRules = findViewById(R.id.addRules);
        addRBS= findViewById(R.id.addRegularBus);
        addEXBS = findViewById(R.id.addExamBus);
        validID = findViewById(R.id.validID);

        adminPass.setOnClickListener(this);
        viewComplains.setOnClickListener(this);
        addNotice.setOnClickListener(this);
        addEM.setOnClickListener(this);
        addRules.setOnClickListener(this);
        addRBS.setOnClickListener(this);
        addEXBS.setOnClickListener(this);
        validID.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.adminPassword)
        {
            startActivity(new Intent(AdminPanel.this, ChangeAP.class));
        } else if (v.getId() == R.id.viewComplains)
        {
            startActivity(new Intent(AdminPanel.this, ComplainView.class));
        } else if (v.getId() == R.id.addNotice)
        {
            startActivity(new Intent(AdminPanel.this, NoticeAdd.class));
        } else if (v.getId() == R.id.addEM)
        {
            startActivity(new Intent(AdminPanel.this, EmergencyAdd.class));
        } else if (v.getId() == R.id.addRules)
        {
            startActivity(new Intent(AdminPanel.this, RulesAdd.class));
        } else if (v.getId() == R.id.addRegularBus)
        {
            startActivity(new Intent(AdminPanel.this, BusScheduleRegularAdd.class));
        } else if (v.getId() == R.id.addExamBus)
        {
            startActivity(new Intent(AdminPanel.this, BusScheduleExamAdd.class));
        } else if (v.getId() == R.id.validID)
        {
            startActivity(new Intent(AdminPanel.this, ValidIDAdd.class));
        }
    }
}

