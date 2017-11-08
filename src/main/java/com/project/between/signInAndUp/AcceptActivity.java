package com.project.between.signInAndUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.between.AnniversaryActivity;
import com.project.between.R;

public class AcceptActivity extends AppCompatActivity {

    Button download_btn;
    EditText myNum_edit;
    EditText friendNum_edit;

    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference tempRoomRef;

    String myNumber;
    String friendNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);
        Intent intent = getIntent();
        myNumber = intent.getStringExtra("myNumber");
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        tempRoomRef = database.getReference("temp").child(myNumber).child("status");


        initView();
        goToProfileActivity();

//        Intent intent = getIntent();
//        myNumber = intent.getExtras().getString("myPhoneMatch");
//        friendNumber = intent.getExtras().getString("friendPhoneMatch");


    }

    public void goToProfileActivity(){

        tempRoomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                 if(snapshot.getValue().equals(myNumber)){
                     Intent intent = new Intent(AcceptActivity.this, ProfileActivity.class);
                     startActivity(intent);
                 }
             }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

//    public void downloadLink() {
//        if (coupleRoomRef.equalTo(friendNumber) == userRef.equalTo(myNumber)) {
//            Intent intent1 = new Intent(AcceptActivity.this, AnniversaryActivity.class);
//            startActivity(intent1);
//        }
//
//    }

    private void initView() {

        download_btn = findViewById(R.id.download_btn);
        myNum_edit = findViewById(R.id.myNum_edit);
        friendNum_edit = findViewById(R.id.friendNum_edit);

    }
}
