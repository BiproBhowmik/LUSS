package com.example.lubussupporter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class BusScheduleRegular extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mDatabase;

    private ProgressBar mainProgressbar, progressBar;

    private ImageView imageView;
    private TextView title, date;
    private Button downloadButton;

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule_regular);

        //back button
        getSupportActionBar().setTitle("Regular Bus Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.regularImage);
        title = findViewById(R.id.regularTitle);
        date = findViewById(R.id.regularDate);
        downloadButton = findViewById(R.id.regularDownloadButton);

        downloadButton.setOnClickListener(this);

        mainProgressbar = findViewById(R.id.regularMainProgressbar);
        Sprite doubleBounce = new DoubleBounce();
        mainProgressbar.setIndeterminateDrawable(doubleBounce);
        mainProgressbar.setVisibility(View.VISIBLE);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Bus Schedule Regular");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Question_StoreToDB upload = dataSnapshot1.getValue(Question_StoreToDB.class);

                    url = upload.getImageUrl();

                    Picasso.with(getApplicationContext())
                            .load(upload.getImageUrl())
                            .placeholder(R.drawable.app_logo)
                            //.fit()
                            //.centerCrop()
                            .into(imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    mainProgressbar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {

                                }
                            });

                    title.setText(upload.getTitle());
                    date.setText(upload.getDate());

                    break;


                }

                //mainProgressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                mainProgressbar.setVisibility(View.INVISIBLE);
            }

        });

    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.regularDownloadButton)
        {
            goToUrl(url);
        }
    }

    private void goToUrl(String s) {
        Uri url = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, url));
    }
}
