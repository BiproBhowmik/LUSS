package com.example.lubussupporter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private CardView question, complain, busTracker, aboutDev, groupChat, notice
            ,emergency, appSetting, feedback, rules, busSchedule, adminChat;
    private TextView luSite;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference, forAdminPass;

    NavigationView navigationView;
    private TextView nav_header_name, nav_header_id;

    public static String stcID, stcUSERNAME;
    private int pressTwice = 0;

    public static String adminPass = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pressTwice = 0;

        question = findViewById(R.id.question);
        complain = findViewById(R.id.complain);
        busTracker = findViewById(R.id.busTracker);
        aboutDev = findViewById(R.id.aboutDev);
        groupChat = findViewById(R.id.groupChat);
        notice = findViewById(R.id.notice);
        emergency = findViewById(R.id.emargency);
        appSetting = findViewById(R.id.appSetting);
        feedback = findViewById(R.id.feedback);
        luSite = findViewById(R.id.luSite);
        rules = findViewById(R.id.rules);
        busSchedule = findViewById(R.id.busSchedule);
        adminChat = findViewById(R.id.adminChat);

        question.setOnClickListener(this);
        complain.setOnClickListener(this);
        busTracker.setOnClickListener(this);
        aboutDev.setOnClickListener(this);
        groupChat.setOnClickListener(this);
        notice.setOnClickListener(this);
        emergency.setOnClickListener(this);
        appSetting.setOnClickListener(this);
        feedback.setOnClickListener(this);
        luSite.setOnClickListener(this);
        rules.setOnClickListener(this);
        busSchedule.setOnClickListener(this);
        adminChat.setOnClickListener(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        forAdminPass = FirebaseDatabase.getInstance().getReference().child("Admin Pass");
        getAdminPass();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        assert firebaseUser != null;
        if (firebaseUser.isEmailVerified())
        {
            hideNavItem();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(new AppSetting().returnTheam(this))));
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        storeSharePref();
    }

    private void getAdminPass() {

        forAdminPass.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    adminPass = dataSnapshot1.getValue().toString();

                    //System.out.println("password"+adminPass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            pressTwice++;

            if(pressTwice>=2)
            {
                this.finish();
            } else {
                Toast.makeText(getApplicationContext(), "Press twice to exit", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Home.this, Login.class));
            finish();
        } else if (id == R.id.nav_about_dev) {
            startActivity(new Intent(Home.this, AboutDevs.class));
        } else if (id == R.id.nav_verification) {

            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Varification Email Send", Toast.LENGTH_LONG).show();
                    Snackbar.make(findViewById(R.id.mainHome), "It can take some extra time to send the mail\nCheck Please", Snackbar.LENGTH_LONG).show();
                }
            });
            //startActivity(new Intent(Home.this, AboutDevs.class));
        } else if (id == R.id.nav_changeap)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            final EditText input = new EditText(this);

            builder.setTitle("Are you an Admin??")
                    .setIcon(R.drawable.app_logo)
                    .setMessage("Input the admin code")
                    .setView(input)
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String txt = input.getText().toString();
                            if (txt.equals(adminPass))
                            {
                                startActivity(new Intent(Home.this, AdminPanel.class));
                            } else
                            {
                                Toast.makeText(Home.this, "Danger", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.question) {
            if(firebaseUser.isEmailVerified())
            {
                startActivity(new Intent(Home.this, MakeQuestion.class));
            }
            else {
                Snackbar.make(v, "Verify your email first", Snackbar.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.complain)
        {
            if(firebaseUser.isEmailVerified())
            {
                startActivity(new Intent(Home.this, ComplainSend.class));
            }
            else {
                Snackbar.make(v, "Verify your email first", Snackbar.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.busTracker)
        {
            Snackbar.make(v, "Bus tracker will add in next update", Snackbar.LENGTH_LONG).show();
        } else if (v.getId() == R.id.aboutDev)
        {
            startActivity(new Intent(Home.this, AboutDevs.class));
        } else if (v.getId() == R.id.groupChat)
        {
            if(firebaseUser.isEmailVerified())
            {
                startActivity(new Intent(Home.this, GroupMessage.class));
            }
            else {
                Snackbar.make(v, "Verify your email first", Snackbar.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.notice)
        {
            startActivity(new Intent(Home.this, NoticeView.class));
            //startActivity(new Intent(Home.this, NoticeAdd.class));
        } else if (v.getId() == R.id.emargency)
        {
            if(firebaseUser.isEmailVerified())
            {
                startActivity(new Intent(Home.this, EmergencyView.class));
            }
            else {
                Snackbar.make(v, "Verify your email first", Snackbar.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.appSetting)
        {
            Snackbar.make(v, "App Setting will add in next update", Snackbar.LENGTH_LONG).show();
            //startActivity(new Intent(Home.this, AppSetting.class));
        } else if (v.getId() == R.id.adminChat)
        {
            Snackbar.make(v, "Admin Chat will add in next update", Snackbar.LENGTH_LONG).show();
            //startActivity(new Intent(Home.this, AppSetting.class));
        } else if (v.getId() == R.id.feedback)
        {
            if(firebaseUser.isEmailVerified())
            {
                startActivity(new Intent(Home.this, Feedback.class));
            }
            else {
                Snackbar.make(v, "Verify your email first", Snackbar.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.rules)
        {
            startActivity(new Intent(Home.this, Rules.class));
        } else if (v.getId() == R.id.busSchedule)
        {
            startActivity(new Intent(Home.this, BusSchedule.class));
        } else if (v.getId() == R.id.luSite)
        {
            Uri url = Uri.parse("https://www.lus.ac.bd/");
            startActivity(new Intent(Intent.ACTION_VIEW, url));
        }
    }

    //Share Pref
    public void storeSharePref() {

        navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        nav_header_name = headerView.findViewById(R.id.nav_header_name);
        nav_header_id = headerView.findViewById(R.id.nav_header_id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    StoreToDB_UsersInfo storeToDB_usersInfo = dataSnapshot1.getValue(StoreToDB_UsersInfo.class);


                    if (firebaseUser.getUid().equals(storeToDB_usersInfo.getUserId())) {

                        //Store SharedPre
                        SharedPreferences sharedPreferences = getSharedPreferences("myFileUNandEM", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("username", storeToDB_usersInfo.getUsernameString());
                        editor.putString("id", storeToDB_usersInfo.getIdString());

                        editor.commit();

                        nav_header_name.setText(storeToDB_usersInfo.getUsernameString());
                        nav_header_id.setText(storeToDB_usersInfo.getIdString());

                        stcID = storeToDB_usersInfo.getIdString();
                        stcUSERNAME = storeToDB_usersInfo.getUsernameString();

                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Somthing wents wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void hideNavItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_verification).setVisible(false);
    }
}
