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
import com.example.allrep.Register_Activity;
import com.example.allrep.userinfo.Userinfo;
import com.example.allrep.userinfo.communityinfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Fragment_community_make extends Fragment {
    Fragment_community_list fragment_community_list;
    DatabaseReference mDBReference = null;
    private LoginViewModel loginViewModel;
    Userinfo user = new Userinfo();
    communityinfo commuinfo = null;

    int numberdata = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_community_make_text, container, false);
        fragment_community_list = new Fragment_community_list();
        mDBReference = FirebaseDatabase.getInstance().getReference();
        EditText writeheadtext = (EditText) root.findViewById(R.id.writeheadtext);
        EditText contentstext = (EditText) root.findViewById(R.id.contentstext);
        Button btn = root.findViewById(R.id.make_text_button);

        // DB내용 number userName wirtetitle writecontent
        //numberdata 찾기
        mDBReference.child("/community_info/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String key = data.getKey();
                    HashMap<String, Object> DBdata = (HashMap<String, Object>) data.getValue();
                    String getdata = DBdata.get("number").toString();
                    //System.out.println(getdata);
                    //System.out.println("---------print");
                    int number = Integer.parseInt(getdata);
                    if (numberdata < number ){
                        numberdata = number;
                    }
                    //System.out.println(numberdata);
                }
                numberdata++;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // 글제목 : writeheadtext  글내용 : contentstext 유저아이디 : user.userId 글번호 : number
        // 글 데이터 저장
        // 글작성
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String writehead = writeheadtext.getText().toString();
                String contents = contentstext.getText().toString();

                commuinfo = new communityinfo(writehead, contents, user.userId, numberdata);

                mDBReference.child("/community_info/").child(Integer.toString(numberdata)).setValue(commuinfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // System.out.println(user.userId);
                        Toast.makeText(root.getContext(), "작성완료!", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putInt("mycommu", 1);
                        fragment_community_list.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.community_container,fragment_community_list).commit();
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
