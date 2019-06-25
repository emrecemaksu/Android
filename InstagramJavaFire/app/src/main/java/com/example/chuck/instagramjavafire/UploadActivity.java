package com.example.chuck.instagramjavafire;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {
    EditText commentText;
    Button shareButton;
    ImageView chooseImage;
    private FirebaseAuth firebaseAuth;
    private StorageReference mStorageRef;
    Uri selected;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            selected = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selected);
                chooseImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        commentText = findViewById(R.id.commentText);
        shareButton = findViewById(R.id.shareButton);
        chooseImage = findViewById(R.id.chooseImage);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // Write a message to the database
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UUID uuid = UUID.randomUUID();
                String imageName = "images/" + uuid + ".jpg";
                UUID randomUUID = UUID.randomUUID();
                final String stringRandom = randomUUID.toString();
                StorageReference storageReference = mStorageRef.child(imageName);
                storageReference.putFile(selected).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String userEmail = firebaseUser.getEmail().toString();
                        String userComment = commentText.getText().toString();
                        myRef.child("Posts").child(stringRandom).child("useremail").setValue(userEmail);
                        myRef.child("Posts").child(stringRandom).child("comment").setValue(userComment);
                        myRef.child("Posts").child(stringRandom).child("downloadurl").setValue(downloadUrl);
                        Toast.makeText(UploadActivity.this, "Post Paylaşıldı...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), FeedActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                   requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
            }
        });
    }
}
