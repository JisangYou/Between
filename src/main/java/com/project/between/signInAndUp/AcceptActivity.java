package com.project.between.signInAndUp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.project.between.R;
import com.project.between.verificationUtil_js.DialogUtil;

public class AcceptActivity extends AppCompatActivity {

    Button download_btn;
    EditText myNum_edit;
    EditText friendNum_edit;
    EditText time_edit;
    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference tempRoomRef;
    String myNumber;
    String friendNumber;

    CountDownTimer countDown = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);
        Intent intent = getIntent();
        myNumber = intent.getStringExtra("myNumber");
        Log.e("mynumber","값 : " + myNumber);
        friendNumber = intent.getStringExtra("friendNumber");
        Log.e("friendnumber","값 : " + friendNumber);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");
        tempRoomRef = database.getReference("temp");

        initView();

        timer();
        goToProfileActivity();


    }

    public void goToProfileActivity() {

        tempRoomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.hasChild("check")) {
                        if (snapshot.child("check").getValue().equals(myNumber)) {

                            Toast.makeText(AcceptActivity.this, "연결되었습니다", Toast.LENGTH_SHORT).show();
                            countDown.cancel(); // 상대방과 연결이 되어서 프로필 액티비티로 갈때에 countdown 중단

                            Intent intent = new Intent(AcceptActivity.this, ProfileActivity.class);
                            intent.putExtra("tempkey2", myNumber);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void downloadLink(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String text = "https://play.google.com/store/apps/details?id=kr.co.vcnc.android.couple";
        intent.putExtra(Intent.EXTRA_TEXT, text);

        Intent chooser = Intent.createChooser(intent, "친구에게 공유하기");
        startActivity(chooser);

    }

    private void initView() {

        download_btn = findViewById(R.id.download_btn);
        myNum_edit = findViewById(R.id.myNum_edit);
        friendNum_edit = findViewById(R.id.friendNum_edit);
        time_edit = findViewById(R.id.time_edit);

    }


    String hours;
    private void timer() {
        countDown = new CountDownTimer(1000 * 60 * 60 * 24, 100) {
            @Override
            public void onTick(long mili) {

                String seconds = "" + ((mili / 1000) % 60);            //초
                String minutes = "" + (((mili / (1000 * 60)) % 60));  //분
                String hours = "" + (((mili / (1000 * 60 * 60)) % 24));//시
                time_edit.setText(hours + " : " + minutes + " : " + seconds);
                Log.e("onTick", "시간이 흐른다!");
            }

            @Override
            public void onFinish() {
                Log.e("onFinish", "시간이 멈춘다!");
                if (hours == "0") {
                    DialogUtil.showDialog("제한 시간이 지났습니다.", AcceptActivity.this, false);
                }
            }
        }.start();
    }


}
