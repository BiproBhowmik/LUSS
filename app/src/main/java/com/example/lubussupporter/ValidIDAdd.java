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

public class ValidIDAdd extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private Button add;

    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid_idadd);

        //back button
        getSupportActionBar().setTitle("Add ID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.editTextSID);
        add = findViewById(R.id.buttonIDAdd);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Valid IDs");

        add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonIDAdd) {
            String sName = name.getText().toString().trim();

            if (!sName.isEmpty()) {
                mProgress = ProgressDialog.show(this, "LU SS", "Adding an ID ...");

                //name.setText(null);

                final String key = mDatabase.push().getKey();
                mDatabase.child(key).setValue(new Rules_StoreToDB(sName));

                Snackbar.make(v, "An ID Added", Snackbar.LENGTH_LONG).show();

                mProgress.dismiss();
            } else {
                //Toast.makeText(getApplicationContext(), "Write Title and Discription", Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Write an ID", Snackbar.LENGTH_LONG).show();
            }
        }

    }
}
