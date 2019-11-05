package com.example.lubussupporter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button signupButton;
    private EditText email, password, confirmPassword, username, id;
    private TextView signin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    private int flag = 0;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setTitle("Create a New Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        signupButton = findViewById(R.id.buttonSignup);
        email = findViewById(R.id.editTextEmailSignup);
        password = findViewById(R.id.editTextPasswordSignup);
        confirmPassword = findViewById(R.id.editTextConfirmPasswordSignup);
        progressBar = findViewById(R.id.progressBar);
        signin = findViewById(R.id.textViewSignin);
        username = findViewById(R.id.editTextUsername);
        id = findViewById(R.id.editTextID);

        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);


        signin.setOnClickListener(this);
        signupButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSignup) {

            final String emailString = email.getText().toString().trim();
            final String passwordString = password.getText().toString().trim();
            final String confirmPasswordString = confirmPassword.getText().toString().trim();
            final String usernameString = username.getText().toString().trim();
            final String idString = id.getText().toString().trim();

            idSearch(idString);

            boolean emailVarification = validationEmail(emailString);
            boolean passwordVarification = validationPassword(passwordString);
            boolean idVarification = validationID();

            if (!emailVarification) {
                email.setError("Enter a valid Email");
                email.requestFocus();
            } else if (idString.length() != 10)
            {
                id.setError("Incorrect LU Student ID");
                id.requestFocus();
            } else if (!passwordVarification) {
                password.setError("Password hasn't to be less than 6 length");
                password.requestFocus();
            } else if (!passwordString.equals(confirmPasswordString))
            {
                confirmPassword.setError("Password didn't match");
                confirmPassword.requestFocus();
            } else if (usernameString.isEmpty())
            {
                username.setError("Fill up it also");
                username.requestFocus();
            }
            else {
                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(emailString, passwordString)//pass email pass
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    userId = firebaseUser.getUid();
                                    reference = FirebaseDatabase.getInstance().getReference("Users");

                                    String key = reference.push().getKey();

                                    StoreToDB_UsersInfo storeToDB_usersInfo = new StoreToDB_UsersInfo(userId, emailString, passwordString, key, usernameString, idString);

                                    reference.child(key).setValue(storeToDB_usersInfo);

                                    progressBar.setVisibility(View.GONE);

                                    Intent intent = new Intent(SignUp.this, Home.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(getApplicationContext(), "Registration completed", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getApplicationContext(), "Not Registered", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }


                            }
                        });

            }


        } else if (v.getId() == R.id.textViewSignin || v.getId() == R.id.textViewAlready) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }

    private boolean validationPassword(String passwordString) {
        if (passwordString.isEmpty() || passwordString.length() < 6) {
            return false;
        }

        return true;
    }

    private boolean validationEmail(String emailString) {
        if (emailString.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            return false;
        }

        return true;
    }

    private boolean validationID() {

        if (flag == 1)
        {
            return true;
        }
        return false;
    }

    private void idSearch(final String idString) {

        DatabaseReference mDatabase;

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Valid IDs");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                flag = 0;

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Rules_StoreToDB upload = dataSnapshot1.getValue(Rules_StoreToDB.class);
                    if(upload.getRule().equals(idString))
                    {
                        System.out.println("xxxxxx"+ flag + upload.getRule());
                        flag = 1;
                        System.out.println("xxxxxx"+ flag + upload.getRule());
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}


