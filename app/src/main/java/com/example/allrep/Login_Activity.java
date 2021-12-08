package com.example.allrep;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.allrep.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login_Activity extends AppCompatActivity {
    DatabaseReference mDBReference = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDBReference = FirebaseDatabase.getInstance().getReference();
        Intent intent_main = new Intent(this, MainActivity.class);
        MainActivity ma = (MainActivity) MainActivity.activity;

        Button login_btn = (Button)findViewById(R.id.login_button);
        EditText login_id = (EditText)findViewById(R.id.login_id);
        EditText login_ps = (EditText)findViewById(R.id.login_pw);
        Button register_btn = (Button)findViewById(R.id.join_button);
        Intent intent = new Intent(this,Register_Activity.class);



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getId = login_id.getText().toString();
                String getPs = login_ps.getText().toString();

                mDBReference.child("/User_info/").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                       for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String key = postSnapshot.getKey();
                            HashMap<String, Object> userInfo = (HashMap<String, Object>) postSnapshot.getValue();
                            String[] getData = {userInfo.get("userId").toString(), userInfo.get("userPs").toString()
                            , userInfo.get("share").toString(), userInfo.get("alram").toString()};
                            if (getData[0].equals(getId) && getData[1].equals(getPs)) {
                                Toast.makeText(Login_Activity.this, "로그인완료!", Toast.LENGTH_SHORT).show();
                                //ma.finish();
                                //자동로그인
                                SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor autoLoginEdit = auto.edit();
                                autoLoginEdit.putString("userId", getData[0]);
                                autoLoginEdit.putString("userPs", getData[1]);
                                autoLoginEdit.putInt("share", Integer.parseInt(getData[2]));
                                autoLoginEdit.putInt("alram", Integer.parseInt(getData[3]));
                                autoLoginEdit.commit();
                                startActivity(intent_main);
                                mDBReference.removeEventListener(this);
                                finish();
                                return;
                            }
                        }
                        Toast.makeText(Login_Activity.this, "로그인 실패!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Database", "Failed to read value.", error.toException());
                    }

                });

            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });


    }

}
