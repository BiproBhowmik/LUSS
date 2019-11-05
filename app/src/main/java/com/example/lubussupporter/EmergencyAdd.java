package com.example.lubussupporter;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmergencyAdd extends AppCompatActivity implements View.OnClickListener {

    private EditText name, number;
    private Button add;

    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_add);

        //back button
        getSupportActionBar().setTitle("Add a Emergency Number");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.editTextEname);
        number = findViewById(R.id.editTextEnumber);
        add = findViewById(R.id.buttonEAdd);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Emergency Numbers");

        add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonEAdd) {
            String sName = name.getText().toString().trim();
            String sNumber = number.getText().toString().trim();

            if (!sName.isEmpty() && !sNumber.isEmpty() /*&& netConnection*/) {
                //mProgress.setMessage("Posting To Blog ... ");
                mProgress = ProgressDialog.show(this, "LU Bus Helper", "Adding a Number ... ");

                name.setText(null);
                number.setText(null);

                final String key = mDatabase.push().getKey();
                mDatabase.child(key).setValue(new Emergency_StoreToDB(sName, sNumber));

                //loveReference.child(key).child(key+uName).setValue(new LoveReact("no", key, 0));
                //startActivity(new Intent(MakeQuestion.this, QuestionsViewer.class));

                //Toast.makeText(getApplicationContext(), "Thank You", Toast.LENGTH_LONG).show();
                Snackbar.make(v, "A Emergency Number Added", Snackbar.LENGTH_LONG).show();

                mProgress.dismiss();
            } else {
                //Toast.makeText(getApplicationContext(), "Write Title and Discription", Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Write Title and Discription", Snackbar.LENGTH_LONG).show();
            }


        }

    }
}
