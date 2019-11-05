package com.example.lubussupporter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BusScheduleRegularAdd extends AppCompatActivity implements View.OnClickListener {

    private EditText title;
    private Button submitButton;
    private ImageButton pictureUpload;
    private Uri imageUri;

    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_schedule_regular_add);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Bus Schedule Regular");
        mStorage = FirebaseStorage.getInstance().getReference();

        //back button
        getSupportActionBar().setTitle("Add a Regular Bus Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.regularTitleID);
        submitButton = findViewById(R.id.regularSend);
        pictureUpload = findViewById(R.id.regularPictureUpload);

        submitButton.setOnClickListener(this);
        pictureUpload.setOnClickListener(this);
    }

    public void onClick(final View v) {
        if (v.getId() == R.id.regularSend) {

            final String title_val = title.getText().toString().trim();

            //chkStatus();

            if (!title_val.isEmpty() && imageUri != null) {
                //mProgress.setMessage("Posting To Blog ... ");
                mProgress = ProgressDialog.show(this, "LU Student Suporter", "Adding Regular Bus Schedule ... ");

                StorageReference filePath = mStorage.child("RegularBusSchedulePictures").child("RegularBusSchedulePictures");//.child(imageUri.getLastPathSegment()/*return image name*/);

                filePath.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                                    Toast.makeText(getApplicationContext(), "Posted Successfully", Toast.LENGTH_LONG).show();
//                                    mProgress.dismiss();
                                // Get a URL to the uploaded content
                                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                                while (!uriTask.isSuccessful()) ;

                                Uri downloadUrl = uriTask.getResult();

                                title.setText(null);

                                //final String key = mDatabase.push().getKey();
                                final String key = "regularbusschedule";

                                String uName = "", aid = "Admin**";

                                //Load SharedPre
                                SharedPreferences sharedPreferences = getSharedPreferences("myFileUNandEM", Context.MODE_PRIVATE);
                                if (sharedPreferences.contains("username")) {

                                    uName = sharedPreferences.getString("username", "Unknown");
                                }

                                LocalDate date = LocalDate.now();
                                LocalTime time = LocalTime.now();

                                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyy");
                                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm a");

                                mDatabase.child(key).setValue(new Question_StoreToDB(title_val, "null", uName,
                                        aid, key,
                                        date.format(dateFormatter),
                                        time.format(timeFormatter), downloadUrl.toString()));

                                Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_LONG).show();

                                mProgress.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                System.out.println("Exception : " + exception);
                                Toast.makeText(getApplicationContext(), "Not Posted", Toast.LENGTH_LONG).show();
                                mProgress.dismiss();
                            }
                        });
            } else {
                //Toast.makeText(getApplicationContext(), "Write Title and Discription", Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Add Image and Title", Snackbar.LENGTH_LONG).show();
            }


        } else if (v.getId() == R.id.regularPictureUpload) {
            Intent galarryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galarryIntent.setType("image/*");
            startActivityForResult(galarryIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            pictureUpload.setImageURI(imageUri);
        }
    }

}
