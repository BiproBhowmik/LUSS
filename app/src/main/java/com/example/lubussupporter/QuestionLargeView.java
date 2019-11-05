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

import org.w3c.dom.Comment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QuestionLargeView extends AppCompatActivity implements View.OnClickListener {

    private String stitle, sdiscription, sUsername, sId, simageURI, sComment, sKey;
    private TextView username, id, title, discription;
    private ImageView imageView;

    private ProgressBar mainProgressbar;

    private EditText commentET;
    private Button commentBT;
    private RecyclerView commentRV;

    private DatabaseReference mDatabase;
    private List<Comment_StoreToDB> databaseList;
    private CommentAdapter myAdapter;

//    private ProgressBar commentProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_large_view);
        //back button
        getSupportActionBar().setTitle("Expended Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Post Comments");
//        commentProgressbar = findViewById(R.id.commentProgressbar);
//        Sprite doubleBounce = new DoubleBounce();
//        commentProgressbar.setIndeterminateDrawable(doubleBounce);
//        commentProgressbar.setVisibility(View.VISIBLE);

        mainProgressbar = findViewById(R.id.lpMainProgressbar);
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

        commentRV = findViewById(R.id.commentRecycelerView);
        commentRV.setHasFixedSize(true);
        commentRV.setLayoutManager(new LinearLayoutManager(this));
//        commentRV.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(this, R.anim.item_layout_animation));
        //recyclerViewFriend.smoothScrollToPosition(0);
        commentRV.smoothScrollToPosition(0);
        //commentRV.setLayoutManager(new VegaLayoutManager());

        username = findViewById(R.id.lpUsername);
        id = findViewById(R.id.lpId);
        title = findViewById(R.id.lpTitle);
        discription = findViewById(R.id.lpDiscription);
        imageView = findViewById(R.id.qlImageView);

        imageView.setOnClickListener(this);

        commentBT = findViewById(R.id.commentButton);
        commentET = findViewById(R.id.commentEditText);

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
        id.setText("Student ID : " + sId + "\n");
        title.setText("Post's Title : " + stitle + "\n");
        discription.setText(sdiscription);

        databaseList = new ArrayList<>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                databaseList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    if(dataSnapshot1.getKey().equals(sKey))
                    {
                        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Post Comments").child(sKey);

                        df.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren())
                                {
                                    Comment_StoreToDB upload = dataSnapshot2.getValue(Comment_StoreToDB.class);
                                    databaseList.add(upload);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                            }
                        });

                        break;
                    }
                }

                myAdapter = new CommentAdapter(getApplicationContext(), databaseList);

                commentRV.setAdapter(myAdapter);
//                commentProgressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
//                commentProgressbar.setVisibility(View.INVISIBLE);
            }

        });

        commentBT.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.commentButton) {
            sComment = commentET.getText().toString();

            if(!sComment.isEmpty()) {

                String uName = "", sid = "";

                //Load SharedPre
                SharedPreferences sharedPreferences = getSharedPreferences("myFileUNandEM", Context.MODE_PRIVATE);
                if (sharedPreferences.contains("username") && sharedPreferences.contains("id")) {

                    uName = sharedPreferences.getString("username", "Unknown");
                    sid = sharedPreferences.getString("id", "Unknown");
                }

                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now();

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm a");

                String tkey = mDatabase.push().getKey();

                mDatabase.child(sKey).child(sid + tkey).setValue(new Comment_StoreToDB(sComment, uName, sid, sKey,
                        date.format(dateFormatter),
                        time.format(timeFormatter)));

                //loveReference.child(key).child(key+uName).setValue(new LoveReact("no", key, 0));
                //startActivity(new Intent(MakeQuestion.this, QuestionsViewer.class));

                Toast.makeText(getApplicationContext(), "Comment has added", Toast.LENGTH_LONG).show();
                commentET.setText(null);

            }

        }

        else if(v.getId() == R.id.qlImageView)
        {
            goToUrl(simageURI);
        }
    }

    private void goToUrl(String s) {
        Uri url = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, url));
    }
}
