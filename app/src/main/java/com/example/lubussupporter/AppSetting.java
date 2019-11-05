package com.example.lubussupporter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.lubussupporter.Home.stcID;
import static com.example.lubussupporter.Home.stcUSERNAME;

public class AppSetting extends AppCompatActivity implements View.OnClickListener {

    private Button green, bringal, pink, dark, whide;
    private DatabaseReference databaseReference;

    public AppSetting() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        //getWindow().getDecorView().setBackgroundColor(Color.parseColor(returnTheam(getApplicationContext())));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Themes");

        green = findViewById(R.id.settingBtnGreen);
        bringal = findViewById(R.id.settingBtnBringal);
        pink = findViewById(R.id.settingBtnPink);
        dark = findViewById(R.id.settingBtnDark);
        whide = findViewById(R.id.settingBtnWhide);

        green.setOnClickListener(this);
        bringal.setOnClickListener(this);
        pink.setOnClickListener(this);
        dark.setOnClickListener(this);
        whide.setOnClickListener(this);
        //back button
//        getSupportActionBar().setTitle("App Setting");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(returnTheam(getApplicationContext()))));
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.settingBtnGreen) {
            //tc = "#1AB16F";

            SQLiteDBHandeler databaseHandler = new SQLiteDBHandeler(this);

            databaseHandler.addData("#1AB16F");

            getWindow().getDecorView().setBackgroundColor(Color.parseColor(returnTheam(getApplicationContext())));

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(returnTheam(getApplicationContext()))));
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        } else if (v.getId() == R.id.settingBtnBringal) {
            //tc = "#232882";
//            SQLiteDBHandeler databaseHandler = new SQLiteDBHandeler(this);
//
//            databaseHandler.addData("#232882");
//
//            themset();
        } else if (v.getId() == R.id.settingBtnPink) {
//            //tc = "#232882";
//            SQLiteDBHandeler databaseHandler = new SQLiteDBHandeler(this);
//
//            databaseHandler.addData("#FF1E50");
//            themset();

        } else if (v.getId() == R.id.settingBtnDark) {
            //tc = "#232882";
            SQLiteDBHandeler databaseHandler = new SQLiteDBHandeler(this);

            databaseHandler.addData("#000000");
            themset();
        } else if (v.getId() == R.id.settingBtnWhide) {
            //tc = "#232882";
            SQLiteDBHandeler databaseHandler = new SQLiteDBHandeler(this);

            databaseHandler.addData("#ffff");
            themset();
        }
    }

    public void themset() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor(returnTheam(getApplicationContext())));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(returnTheam(getApplicationContext()))));

    }

    public String returnTheam(Context context) {

        SQLiteDBHandeler databaseHandler = new SQLiteDBHandeler(this);
        Cursor cursor = databaseHandler.displayAllData();

        if(cursor.getCount() == 0){
            return "#232882";
        } else
        {
            StringBuilder stringBuffer = new StringBuilder();
            while (cursor.moveToNext()) {
                stringBuffer.append(cursor.getString(1));
            }
            if (!stringBuffer.toString().isEmpty())
                return stringBuffer.toString();
        }


        //showData("The Record's", stringBuffer.toString());
        //showData("ks", "slk");

        return "#232882";
    }

    public void showData(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }



}
