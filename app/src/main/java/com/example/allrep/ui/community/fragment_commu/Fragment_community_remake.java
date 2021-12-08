package com.example.allrep.ui.community.fragment_commu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.allrep.LoginViewModel;
import com.example.allrep.R;
import com.example.allrep.userinfo.Userinfo;
import com.example.allrep.userinfo.communityinfo;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class Fragment_community_remake extends Fragment {
    DatabaseReference mDBReference = null;
    Fragment_community_text fragment_community_text;
    private LoginViewModel loginViewModel;
    Userinfo user = new Userinfo();
    communityinfo commuinfo = null;

    // 데이터
    // remakeheadtext : 글 제목  remakecontentstext : 글 내용  remake_text_button : 저장 버튼 변수
    // 게시글 변경부분 왠만하면 변경하지 않는경우.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_community_remake_text, container, false);
        mDBReference = FirebaseDatabase.getInstance().getReference();
        fragment_community_text = new Fragment_community_text();
        EditText remakehead = (EditText) root.findViewById(R.id.remakeheadtext);
        EditText remakecontent = (EditText) root.findViewById(R.id.remakecontentstext);
        Button remakebtn = root.findViewById(R.id.remake_text_button);

        // 리스트 뷰의 클릭하면 개시글 번호 확인하는 숫자
        int remakenumber = getArguments().getInt("thrownumber");

        mDBReference.child("/community_info/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot DBdata : snapshot.getChildren()) {
                    String key = DBdata.getKey();
                    HashMap<String, Object> cummdata = (HashMap<String, Object>) DBdata.getValue();
                    String[] getData = {cummdata.get("number").toString(), cummdata.get("userName").toString(),
                                    cummdata.get("wirtetitle").toString(), cummdata.get("writecontent").toString()};
                    // 가져오는 순서 0:숫자 1:유저 2:제목 3:내용
                    String number = getData[0];
                    int numberdata = Integer.parseInt(number);
                    String title = getData[2];
                    String content = getData[3];
                    // 찾아서 번호 수 확인후 데이터를 텍스트로 옮긴다.
                    if (remakenumber == numberdata) {
                        remakehead.setText(title);
                        remakecontent.setText(content);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        remakebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rewritehead = remakehead.getText().toString();
                String recontents = remakecontent.getText().toString();

                commuinfo = new communityinfo(rewritehead, recontents, user.userId, remakenumber);

                mDBReference.child("/community_info/").child(Integer.toString(remakenumber)).setValue(commuinfo).
                            addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("thrownumber",remakenumber);
                        fragment_community_text.setArguments(bundle);
                        Toast.makeText(root.getContext(), "변경완료!", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.community_container,fragment_community_text).commit();
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
