package com.example.allrep.ui.option;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.allrep.LoginViewModel;
import com.example.allrep.Login_Activity;
import com.example.allrep.MainActivity;
import com.example.allrep.R;
import com.example.allrep.Register_Activity;
import com.example.allrep.userinfo.Userinfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Option extends Fragment {

    private LoginViewModel loginViewModel;
    TextView userID;
    EditText userPW;
    EditText userPWch;
    RadioGroup shareGroup;
    RadioButton r1, r2 , r3;
    CheckBox ch1;
    CheckBox ch2;
    Button save_btn;
    DatabaseReference mDBReference = null;
    int selectedRadio = 0;
    int selectedCheck = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_option, container, false);
        mDBReference = FirebaseDatabase.getInstance().getReference();
        userID = (TextView)root.findViewById(R.id.userid);
        userPW = (EditText)root.findViewById(R.id.userpassword);
        userPWch = (EditText)root.findViewById(R.id.userpasswordcheck);
        shareGroup = (RadioGroup)root.findViewById(R.id.sharegroup);
        r1 = (RadioButton)root.findViewById(R.id.radioButton1);
        r2 = (RadioButton)root.findViewById(R.id.radioButton2);
        r3 = (RadioButton)root.findViewById(R.id.radioButton3);


        ch1 = (CheckBox)root.findViewById(R.id.checkBox1);
        ch2 = (CheckBox)root.findViewById(R.id.checkBox2);
        save_btn = (Button)root.findViewById(R.id.save_option);



        shareGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.radioButton1:
                        selectedRadio = 2;
                        break;
                    case R.id.radioButton2:
                        selectedRadio = 1;
                        break;
                    case R.id.radioButton3:
                        selectedRadio = 0;
                        break;
                }
            }
        });



        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedCheck = 0;
                if(ch1.isChecked()){
                    selectedCheck += 1;
                }
                if(ch2.isChecked()){
                    selectedCheck += 2;
                }
                if(!userPW.getText().toString().equals(userPWch.getText().toString())){
                    Toast.makeText(getContext(), "비밀번호를 확인해 주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userPW.getText().toString().length() < 8){
                    Toast.makeText(getContext(), "비밀번호는 8글자 이상!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Userinfo user = new Userinfo(userID.getText().toString(),userPW.getText().toString(),selectedRadio,selectedCheck, "");

                mDBReference.child("/User_info/").child(userID.getText().toString()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "변경완료!", Toast.LENGTH_SHORT).show();
                        PackageManager packageManager = getActivity().getPackageManager();
                        Intent intent = packageManager.getLaunchIntentForPackage(getActivity().getPackageName());
                        ComponentName componentName = intent.getComponent();
                        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                        SharedPreferences auto = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLoginEdit = auto.edit();
                        autoLoginEdit.clear();
                        autoLoginEdit.commit();
                        startActivity(mainIntent);
                        System.exit(0);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "저장실패!", Toast.LENGTH_SHORT).show();
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
            public void onChanged(Userinfo user) {
                userID.setText(user.userId);
                userPW.setEnabled(true);
                userPWch.setEnabled(true);
                shareGroup.setEnabled(true);
                r1.setEnabled(true);
                r2.setEnabled(true);
                r3.setEnabled(true);
                ch1.setEnabled(true);
                ch2.setEnabled(true);
                switch (user.share){
                    case 0:
                        r3.setChecked(true);
                        break;
                    case 1:
                        r2.setChecked(true);
                        break;
                    case 2:
                        r1.setChecked(true);
                        break;
                }
                switch (user.alram){
                    case 1:
                        ch1.setChecked(true);
                        break;
                    case 2:
                        ch2.setChecked(true);
                        break;
                    case 3:
                        ch1.setChecked(true);
                        ch2.setChecked(true);
                        break;
                }

                save_btn.setEnabled(true);
            }
        });
    }
}