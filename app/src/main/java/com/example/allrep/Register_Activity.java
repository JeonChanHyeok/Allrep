package com.example.allrep;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allrep.userinfo.Userinfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity {
    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    Userinfo userInfo = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDBReference = FirebaseDatabase.getInstance().getReference();
        childUpdates = new HashMap<>();

        Button join_btn = (Button)findViewById(R.id.join_button);
        EditText user_id = (EditText)findViewById(R.id.join_id);
        EditText user_ps = (EditText)findViewById(R.id.join_pw);
        EditText user_ps2 = (EditText)findViewById(R.id.join_pw2);

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getUserId = user_id.getText().toString();
                String getUserPs = user_ps.getText().toString();
                String getUserPs2 = user_ps2.getText().toString();

                if (!getUserPs.equals(getUserPs2)) {
                    Toast.makeText(Register_Activity.this, "비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getUserId.length() < 8) {
                    Toast.makeText(Register_Activity.this, "ID는 8글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getUserPs.length() < 8) {
                    Toast.makeText(Register_Activity.this, "PW는 8글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                userInfo = new Userinfo(getUserId, getUserPs);

                mDBReference.child("/User_info/").child(getUserId).child("userId").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if (value != null) {
                            Toast.makeText(Register_Activity.this, "중복된 아이디 입니다!", Toast.LENGTH_SHORT).show();
                        } else {
                            mDBReference.child("/User_info/").child(getUserId).setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register_Activity.this, "가입완료!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register_Activity.this, "가입실패!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
