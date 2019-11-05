package com.example.lubussupporter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GroupMessage extends AppCompatActivity implements View.OnClickListener {

    private EditText message;
    private ImageView sent;

    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference, reference;

    private List<GroupMessageStore> mChats;
    private RecyclerView messageRecyView;

    private String sentUserID, currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);

        mChats = new ArrayList<>();
        messageRecyView = findViewById(R.id.messageRecyView);
        messageRecyView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        messageRecyView.setLayoutManager(linearLayoutManager);

        //back button
        getSupportActionBar().setTitle("Group Messenger");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Group Messages");

        message = findViewById(R.id.editTextMessage);
        sent = findViewById(R.id.imageButtonSentMessage);

        sent.setOnClickListener(this);
        showMessage();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.imageButtonSentMessage)
        {
            String messageS = message.getText().toString();

            if(!messageS.isEmpty())
            {

                SharedPreferences sharedPreferences = getSharedPreferences("myFileUNandEM", Context.MODE_PRIVATE);
                String userName = sharedPreferences.getString("username", "Unknown");
                String id = sharedPreferences.getString("id", "Unknown");

                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now();

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss a");

                GroupMessageStore messageStore = new GroupMessageStore(userName, messageS, id, date.format(dateFormatter),
                        time.format(timeFormatter));

                String key = databaseReference.push().getKey();

                databaseReference.child(key).setValue(messageStore);
                //Toast.makeText(getApplicationContext(), "Message Stored", Toast.LENGTH_SHORT).show();
                message.setText(null);
            }
        }
    }

    private void showMessage()
    {
        reference = FirebaseDatabase.getInstance().getReference().child("Group Messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mChats.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    GroupMessageStore upload = dataSnapshot1.getValue(GroupMessageStore.class);
                    mChats.add(upload);
                }

                GroupMessageAdapter myAdapter = new GroupMessageAdapter(GroupMessage.this, mChats);
                messageRecyView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error" + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
