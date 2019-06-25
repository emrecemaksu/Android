package com.example.chuck.instagramjavafire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedActivity extends AppCompatActivity {
    ArrayList<String> userEmailFromFB;
    ArrayList<String> userImageFromFB;
    ArrayList<String> userCommentFromFB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ListView listView;
    PostClass adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_post, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_post) {
            Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        userEmailFromFB = new ArrayList<String>();
        userCommentFromFB = new ArrayList<String>();
        userImageFromFB = new ArrayList<String>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        getDataFromFirebase();
        listView = findViewById(R.id.listView);
        adapter = new PostClass(userEmailFromFB, userImageFromFB, userCommentFromFB, this);
        listView.setAdapter(adapter);
    }
    protected void getDataFromFirebase(){
        DatabaseReference reference = firebaseDatabase.getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) snapshot.getValue();
                    userEmailFromFB.add(hashMap.get("usermail"));
                    userCommentFromFB.add(hashMap.get("comment"));
                    userImageFromFB.add(hashMap.get("downloadurl"));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}