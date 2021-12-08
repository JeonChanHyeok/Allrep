package com.example.allrep.ui.community.fragment_commu;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.allrep.LoginViewModel;
import com.example.allrep.R;
import com.example.allrep.commuinfo.Commeinfo;
import com.example.allrep.userinfo.Userinfo;
import com.example.allrep.userinfo.communityinfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_community_text extends Fragment {
    DatabaseReference mDBReference = null;
    private LoginViewModel loginViewModel;
    Fragment_community_make fragment_community_make;
    Fragment_community_list fragment_community_list;
    Userinfo user;
    String name;
    Commeinfo commeInfo = null;
    Comment_adapter ca;
    int numberdata = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDBReference = FirebaseDatabase.getInstance().getReference();
        View root = inflater.inflate(R.layout.fragment_community_text, container, false);
        Button remove_btn = (Button)root.findViewById(R.id.remove_text);
        Button remake_btn = (Button)root.findViewById(R.id.remake_text);
        TextView text_title = (TextView)root.findViewById(R.id.text_title);
        TextView text_user_name = (TextView)root.findViewById(R.id.text_user_name);
        TextView community_text = (TextView)root.findViewById(R.id.community_text);
        TextView text_no = (TextView)root.findViewById(R.id.text_num);
        EditText comme_text = (EditText)root.findViewById(R.id.comme_text);
        Button comme_text_btn = (Button)root.findViewById(R.id.comme_text_btn);
        ListView comment_list = (ListView) root.findViewById(R.id.comment_list);
        ca = new Comment_adapter();



        int numberA = getArguments().getInt("thrownumber");

        // DB내용 number userName wirtetitle writecontent
        mDBReference.child("/community_info/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot DBdata : snapshot.getChildren()) {
                    String key = DBdata.getKey();
                    HashMap<String, Object> cummdata = (HashMap<String, Object>) DBdata.getValue();
                    String[] getData = {cummdata.get("number").toString(), cummdata.get("userName").toString(), cummdata.get("wirtetitle").toString(), cummdata.get("writecontent").toString()};
                    String number = getData[0];
                    int numberB = Integer.parseInt(number);
                    name = getData[1];
                    String title = getData[2];
                    String content = getData[3];
                    //System.out.println(user.userId);
                    //System.out.println(name);
                    //System.out.println(numberA);
                    //System.out.println(numberB);

                    // text_title 타이틀 text_user_name 유저네임 community_text 내용 text_num 텍스트번호
                    if (numberA == numberB){
                        text_title.setText(title);
                        text_user_name.setText(name);
                        community_text.setText(content);
                        text_no.setText(Integer.toString(numberB));
                        if (user.userId.equals(name)){
                            remove_btn.setEnabled(true);
                            remake_btn.setEnabled(true);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDBReference.child("/Comme_info/").child(Integer.toString(numberA)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String key = data.getKey();
                    HashMap<String, Object> commeinfo = (HashMap<String, Object>) data.getValue();
                    String[] getdata = {commeinfo.get("number").toString(), commeinfo.get("userId").toString(), commeinfo.get("commetext").toString(), commeinfo.get("num").toString()};
                    if(numberA == Integer.parseInt(getdata[0])){
                        Commeinfo com = new Commeinfo(Integer.parseInt(getdata[0]), Integer.parseInt(getdata[3]), getdata[1], getdata[2]);
                        ca.item.add(com);
                    }
                }
                comment_list.setAdapter(ca);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mDBReference.child("/Comme_info/").child(Integer.toString(numberA)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String key = data.getKey();
                    HashMap<String, Object> userInfo = (HashMap<String, Object>) data.getValue();
                    String getdata = userInfo.get("num").toString();
                    int number = Integer.parseInt(getdata);
                    if (numberdata < number ){
                        numberdata = number;
                    }
                    System.out.println(numberdata);
                }
                System.out.println(numberdata);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        comme_text_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {


                String CommeText = comme_text.getText().toString();
                numberdata++;
                commeInfo = new Commeinfo(numberA, numberdata, user.userId, CommeText);

                mDBReference.child("/Comme_info/").child(Integer.toString(numberA)).child(Integer.toString(numberdata)).setValue(commeInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid2) {
                        System.out.println(numberdata);
                        Toast.makeText(root.getContext(), "작성완료", Toast.LENGTH_SHORT).show();
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
