package com.project.between.activity.logIn_activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.project.between.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.between.activity.HomeActivity;
import com.project.between.util.ConstantUtil;
import com.project.between.util.PreferenceUtil;

public class SignIn2Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference myRef;


    private Button login_btn;
    private EditText signUp_email_edit;
    private EditText signUp_password_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in2);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(ConstantUtil.USER);

        initview();

    }

    public void initview() {
        signUp_email_edit = (EditText) findViewById(R.id.signUp_email_edit);
        signUp_password_edit = (EditText) findViewById(R.id.signUp_password_edit);
        login_btn = (Button) findViewById(R.id.login_btn);

    }

    public void signin(View view) {
        final String email = signUp_email_edit.getText().toString();
        final String password = signUp_password_edit.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            myRef = database.getReference(ConstantUtil.USER).child(email.replace(".", "_"));
                            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String roomID = dataSnapshot.child(ConstantUtil.ROOM_ID).child(ConstantUtil.ID).getValue(String.class);
                                    //String photoRoom = dataSnapshot.child("photoID").getValue(String.class);
                                    String myNum = dataSnapshot.child(ConstantUtil.MY_PHONE).getValue(String.class);
                                    PreferenceUtil.setValue(SignIn2Activity.this, ConstantUtil.CHAT_ROOM, roomID);
                                    PreferenceUtil.setValue(SignIn2Activity.this, ConstantUtil.MY_NUMBER, myNum);
                                    PreferenceUtil.setValue(SignIn2Activity.this, ConstantUtil.USER_EMAIL, email);
                                    PreferenceUtil.setValue(SignIn2Activity.this, ConstantUtil.PASSWORD, password);
                                    PreferenceUtil.setValue(SignIn2Activity.this, ConstantUtil.AUTO_SIGN_IN, "true");
                                    //PreferenceUtil.setValue(SignIn2Activity.this, "photoroom", photoRoom);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                            // 이메일 검증 확인
//                            if (user.isEmailVerified()) {
//                                // 다음 페이지로 이동
//                                Intent intent = new Intent(SignIn2Activity.this, HomeActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Toast.makeText(SignIn2Activity.this
//                                        , "이메일을 확인하셔야 합니다"
//                                        , Toast.LENGTH_SHORT).show();
//                            }

                            Intent intent = new Intent(SignIn2Activity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignIn2Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}