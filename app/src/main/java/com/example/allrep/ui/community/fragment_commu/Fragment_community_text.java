package com.example.allrep.ui.community.fragment_commu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.allrep.LoginViewModel;
import com.example.allrep.R;
import com.example.allrep.userinfo.Commeinfo;
import com.example.allrep.userinfo.Userinfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Fragment_community_text extends Fragment {
    DatabaseReference mDBReference = null;
    private LoginViewModel loginViewModel;
    Userinfo user;
    HashMap<String, Object> childUpdates = null;
    Commeinfo commentInfo = null;
    int numberdata = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDBReference = FirebaseDatabase.getInstance().getReference();
        childUpdates = new HashMap<>();

        View root = inflater.inflate(R.layout.fragment_community_text, container, false);
        TextView text_title = (TextView)root.findViewById(R.id.text_title);
        TextView text_no = (TextView)root.findViewById(R.id.text_no);
        TextView text_user_name = (TextView)root.findViewById(R.id.text_user_name);
        TextView text_date = (TextView)root.findViewById(R.id.text_date);
        Button remove_btn = (Button)root.findViewById(R.id.remove_text);
        Button remake_btn = (Button)root.findViewById(R.id.remake_text);
        EditText comme_text = (EditText)root.findViewById(R.id.comme_text);
        Button comme_text_btn = (Button)root.findViewById(R.id.comme_text_btn);

        mDBReference.child("/community_info/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String key = data.getKey();
                    HashMap<String, Object> userInfo = (HashMap<String, Object>) data.getValue();
                    String getdata = userInfo.get("text_no").toString();
                    //System.out.println(getdata);
                    //System.out.println("---------print");
                    numberdata = Integer.parseInt(getdata);
                    numberdata++;
                    //System.out.println(numberdata);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        comme_text_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getCommeText = comme_text.getText().toString();
                if (getCommeText.length() < 1) {
                    Toast.makeText(getActivity(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }


                commentInfo = new Commeinfo(numberdata, user.userId, getCommeText);

                mDBReference.child("/Comment_info/").child(getCommeText).child("commetext").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if (value != null) {
                            mDBReference.child("/Comment_info/").child(getCommeText).setValue(commentInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "작성완료", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "작성실패", Toast.LENGTH_SHORT).show();
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



        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        loginViewModel.getLoginedId().observe(getViewLifecycleOwner(), new Observer<Userinfo>() {
            @Override
            public void onChanged(Userinfo s) {
                user = s;
            }
        });
    }
}
