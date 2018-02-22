package com.project.between.activity.logIn_activity;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.between.util.ConstantUtil;
import com.project.between.util.PreferenceUtil;
import com.project.between.R;

public class PhoneConnectActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference userRef;
    DatabaseReference tempRoomRef;
    DatabaseReference chatRoomRef;
    DatabaseReference veriRef;
    private EditText myNum_edit;
    private EditText friendNum_edit;
    private EditText friendEmail_edit;
    private Button phone_Connect_btn;
    String tempkey;
    ImageButton phoneNum_img_btn;
    TextView friendPhone_text;
    String phonenumber_local;
    boolean phoneNumberCheck;
    boolean phoneNumberCheck2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_connect);
        initView();

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(ConstantUtil.USER);
        tempRoomRef = database.getReference(ConstantUtil.TEMP_CHECK);
        chatRoomRef = database.getReference(ConstantUtil.CHAT_ROOM);

        Intent intent = getIntent();
        tempkey = intent.getExtras().getString(ConstantUtil.TEMP_KEY);
        loadPhoneNumberLocalDatabase();
        checkPhoneNumber();
    }

    private void initView() {
        myNum_edit = (EditText) findViewById(R.id.myNum_edit);
        friendNum_edit = (EditText) findViewById(R.id.friendNum_edit);
        phone_Connect_btn = (Button) findViewById(R.id.phone_Connect_btn);
        phoneNum_img_btn = (ImageButton) findViewById(R.id.phoneNum_img_btn);
        friendPhone_text = (TextView) findViewById(R.id.friendPhone_text);
        friendEmail_edit = findViewById(R.id.friendEmail_text);
    }

    public void onConnect(View view) {

        searchTemp();

    }

    private void searchTemp() {
        final String myNumber = myNum_edit.getText().toString();
        final String friendNumber = friendNum_edit.getText().toString();
        final String friendEmail = friendEmail_edit.getText().toString().replace(".", "_");

        userRef.child(tempkey).child(ConstantUtil.MY_PHONE).setValue(myNumber);
        userRef.child(tempkey).child(ConstantUtil.FRIEND_PHONE).setValue(friendNumber);

        tempRoomRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(friendNumber) && !dataSnapshot.hasChild(friendEmail)) {
                    Toast.makeText(PhoneConnectActivity.this, "가진 사용자 없음", Toast.LENGTH_LONG).show();
                    dataSnapshot.child(myNumber).child(ConstantUtil.FRIEND_PHONE).getRef().setValue(friendNumber);
                    dataSnapshot.child(myNumber).child("status").child("confirm").getRef().setValue("none");
                    String chatRoom = myNumber + friendNumber + "chat";
                    String photoRoom = myNumber + friendNumber + "photo";

                    userRef.child(tempkey).child(ConstantUtil.ROOM_ID).child(ConstantUtil.ID).setValue(chatRoom);
                    userRef.child(tempkey).child(ConstantUtil.PHOTO_ID).child(ConstantUtil.ID).setValue(photoRoom);

                    userRef.child(tempkey).child(ConstantUtil.FRIEND_EMAIL).setValue(friendEmail);
                    SignUpActivity.user.setFriend_phone(friendNumber);
                    SignUpActivity.user.setPhone(myNumber);
                    SignUpActivity.user.setFriend_email(friendEmail);


                    PreferenceUtil.setValue(PhoneConnectActivity.this, ConstantUtil.CHAT_ROOM, chatRoom);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, ConstantUtil.MY_NUMBER, myNumber);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, ConstantUtil.PHOTO_ROOM, photoRoom);


                    moveToAcceptActivity(myNumber, friendNumber);

                } else {
                    Toast.makeText(PhoneConnectActivity.this, "가진 사용자 있음", Toast.LENGTH_LONG).show();
                    dataSnapshot.child(friendNumber).child("status").child("confirm").getRef().setValue("yes");
                    String chatRoom = friendNumber + myNumber + "chat";
                    String photoRoom = friendNumber + myNumber + "photo";
                    userRef.child(tempkey).child(ConstantUtil.ROOM_ID).child(ConstantUtil.ID).setValue(friendNumber + myNumber + "chat");
                    userRef.child(tempkey).child(ConstantUtil.PHOTO_ID).child(ConstantUtil.ID).setValue(photoRoom);

                    userRef.child(tempkey).child(ConstantUtil.FRIEND_EMAIL).setValue(friendEmail);
                    SignUpActivity.user.setFriend_phone(friendNumber);
                    SignUpActivity.user.setPhone(myNumber);
                    SignUpActivity.user.setFriend_email(friendEmail);


                    PreferenceUtil.setValue(PhoneConnectActivity.this, ConstantUtil.CHAT_ROOM, chatRoom);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, ConstantUtil.MY_NUMBER, myNumber);
                    PreferenceUtil.setValue(PhoneConnectActivity.this, ConstantUtil.PHOTO_ROOM, photoRoom);
                    moveToProfileActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkPhoneNumber() {
        myNum_edit.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        myNum_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 13) {
                    phoneNumberCheck = true;
                } else {
                    phoneNumberCheck = false;
                }

            }
        });

        friendNum_edit.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        friendNum_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 13) {
                    phoneNumberCheck2 = true;
                } else {
                    phoneNumberCheck2 = false;
                }
            }
        });
    }

    public void moveToProfileActivity() {

        Intent intent = new Intent(PhoneConnectActivity.this, ProfileActivity.class);
        intent.putExtra(ConstantUtil.TEMP_KEY2, tempkey);
        startActivity(intent);
    }


    public void moveToAcceptActivity(String myNumber, String friendNumber) {
        Intent intent = new Intent(PhoneConnectActivity.this, AcceptActivity.class);
        intent.putExtra(ConstantUtil.MY_PHONE_NUMBER, myNumber);
        intent.putExtra(ConstantUtil.FRIEND_PHONE_NUMBER, friendNumber);
        intent.putExtra(ConstantUtil.TEMP_KEY2, tempkey);
        startActivity(intent);
    }

    public void loadPhoneNumberLocalDatabase() {

        phoneNum_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                friendNum_edit.setText(phonenumber_local);
                startActivityForResult(intent, 1);
            }
        });
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();

            phonenumber_local = cursor.getString(0);

            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
