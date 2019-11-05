package com.example.lubussupporter;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RulesAdd extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private Button add;

    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_add);

        //back button
        getSupportActionBar().setTitle("Add Rules");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.editTextRname);
        add = findViewById(R.id.buttonRAdd);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Rules");

        add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonRAdd) {
            String sName = name.getText().toString().trim();

            if (!sName.isEmpty()) {
                mProgress = ProgressDialog.show(this, "LU SS", "Adding a Rule ...");

                name.setText(null);

                final String key = mDatabase.push().getKey();
                mDatabase.child(key).setValue(new Rules_StoreToDB(sName));

                Snackbar.make(v, "A Rule Added", Snackbar.LENGTH_LONG).show();

                mProgress.dismiss();
            } else {
                //Toast.makeText(getApplicationContext(), "Write Title and Discription", Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Write a Rule", Snackbar.LENGTH_LONG).show();
            }
        }

    }
}
