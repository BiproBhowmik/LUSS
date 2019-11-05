package com.example.lubussupporter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeAP extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextPass;
    private Button buttonPass;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ap);

        //back button
        getSupportActionBar().setTitle("Change Admin Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Admin Pass");

        editTextPass = findViewById(R.id.editPass);
        buttonPass = findViewById(R.id.buttonPass);

        buttonPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonPass)
        {
            databaseReference.child("Password").setValue(editTextPass.getText().toString());
            Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();
        }
    }
}
