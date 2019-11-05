package com.example.lubussupporter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NoticeLargeView extends AppCompatActivity implements View.OnClickListener {

    private String stitle, sdiscription, sUsername, sId, simageURI, sComment, sKey;
    private TextView username, id, title, discription;
    private ImageView imageView;

    private ProgressBar mainProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_large_view);
        //back button
        getSupportActionBar().setTitle("Expended Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainProgressbar = findViewById(R.id.noticeLargeMainProgressbar);
        Sprite doubleBounce = new DoubleBounce();
        mainProgressbar.setIndeterminateDrawable(doubleBounce);
        mainProgressbar.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        stitle = bundle.getString("title");
        sdiscription = bundle.getString("discription");
        sUsername = bundle.getString("username");
        sId = bundle.getString("id");
        simageURI = bundle.getString("image");
        sKey = bundle.getString("key");

        username = findViewById(R.id.noticeLargeUsername);
        id = findViewById(R.id.noticeLargeId);
        title = findViewById(R.id.noticeLargeTitle);
        discription = findViewById(R.id.noticeLargeDiscription);
        imageView = findViewById(R.id.noticeLargeimageView);

        imageView.setOnClickListener(this);

        Picasso.with(getApplicationContext())
                .load(simageURI)
                .placeholder(R.drawable.app_logo)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mainProgressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        username.setText("Name : " + sUsername);
        id.setText("ID : " + sId + "\n");
        title.setText("Post's Title : " + stitle + "\n");
        discription.setText(sdiscription);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.noticeLargeimageView)
        {
            goToUrl(simageURI);
        }
    }

    private void goToUrl(String s) {
        Uri url = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, url));
    }
}
