package com.example.lubussupporter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private final int delay = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (chkStatus()) {
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                } else {
                    Snackbar.make(findViewById(R.id.fontView), "Please Check Your Internet Connection And try again\nThank you", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        }, delay);

    }

    private boolean chkStatus() {
        final ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnectedOrConnecting()) {
            return true;
        } else if (mobile.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
