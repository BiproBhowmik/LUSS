package com.example.lubussupporter;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MakeQuestion extends AppCompatActivity implements View.OnClickListener {

    private EditText title, discription;
    private Button submitButton, viewPosts;
    private ImageButton pictureUpload;
    private Uri imageUri;
    private String autoURL = "https://firebasestorage.googleapis.com/v0/b/lubussupporter.appspot.com/o/NoPicture%2Fno_logo.jpg?alt=media&token=91af9517-adcc-4785-8856-d1caaa97d7c6";

    private ProgressDialog mProgress;

    private DatabaseReference mDatabase, loveReference;
    private StorageReference mStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_question);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("QuestionsOrPosts");
        //loveReference = FirebaseDatabase.getInstance().getReference().child("Love Reacts");
        mStorage = FirebaseStorage.getInstance().getReference();

        //back button
        getSupportActionBar().setTitle("Post Your Idea Or Question");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.titleID);
        discription = findViewById(R.id.decID);
        submitButton = findViewById(R.id.submitButton);
        viewPosts = findViewById(R.id.viewPosts);
        pictureUpload = findViewById(R.id.pictureUpload);

        submitButton.setOnClickListener(this);
        viewPosts.setOnClickListener(this);
        pictureUpload.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitButton) {
            //submitButton.startAnimation(animation);

            final String title_val = title.getText().toString().trim();
            final String dicription_val = discription.getText().toString().trim();

            //chkStatus();

            if (!title_val.isEmpty() && !dicription_val.isEmpty() /*&& netConnection*/) {
                //mProgress.setMessage("Posting To Blog ... ");
                mProgress = ProgressDialog.show(this, "LU Student Supporter", "Posting ... ");

                if (imageUri != null) {
                    StorageReference filePath = mStorage.child("QuestionsOrPostsPictures").child(imageUri.getLastPathSegment()/*return image name*/);

                    filePath.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                                    discription.setText(null);

                                    final String key = mDatabase.push().getKey();

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

                                    mDatabase.child(key).setValue(new Question_StoreToDB(title_val, dicription_val, uName,
                                            sid, key,
                                            date.format(dateFormatter),
                                            time.format(timeFormatter), downloadUrl.toString()));

                                    //loveReference.child(key).child(key+uName).setValue(new LoveReact("no", key, 0));
                                    startActivity(new Intent(MakeQuestion.this, QuestionsViewer.class));

                                    Toast.makeText(getApplicationContext(), "Posted Successfully", Toast.LENGTH_LONG).show();

                                    mProgress.dismiss();

//                                    loveReference.child(key).child(key+uName).setValue(new Love("no", key, 0));
//                                    startActivity(new Intent(Post_Activity.this, Main2Activity.class));
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    // ...
                                    System.out.println("Exception : " + exception);
                                    Toast.makeText(getApplicationContext(), "Not Posted", Toast.LENGTH_LONG).show();
                                    mProgress.dismiss();
                                }
                            });
                } else {
                    title.setText(null);
                    discription.setText(null);

                    final String key = mDatabase.push().getKey();

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

                    mDatabase.child(key).setValue(new Question_StoreToDB(title_val, dicription_val, uName,
                            sid, key,
                            date.format(dateFormatter),
                            time.format(timeFormatter), autoURL));

                    //loveReference.child(key).child(key+uName).setValue(new LoveReact("no", key, 0));
                    startActivity(new Intent(MakeQuestion.this, QuestionsViewer.class));

                    Toast.makeText(getApplicationContext(), "Posted Successfully", Toast.LENGTH_LONG).show();

                    mProgress.dismiss();
                }


            } else {
                //Toast.makeText(getApplicationContext(), "Write Title and Discription", Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Write Title and Discription", Snackbar.LENGTH_LONG).show();
            }

        } else if (v.getId() == R.id.viewPosts) {
            startActivity(new Intent(MakeQuestion.this, QuestionsViewer.class));
        } else if (v.getId() == R.id.pictureUpload) {
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
