package com.example.allrep.ui.community.fragment_commu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.allrep.LoginViewModel;
import com.example.allrep.R;
import com.example.allrep.commuinfo.Commuinfo;
import com.example.allrep.userinfo.Userinfo;
import com.google.firebase.database.DatabaseReference;

public class Fragment_community_make extends Fragment {
    DatabaseReference mDBReference = null;
    private LoginViewModel loginViewModel;
    Button save;
    Userinfo user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_community_make_text, container, false);
        save = (Button)root.findViewById(R.id.make_text_button);
        EditText title = (EditText)root.findViewById(R.id.writeheadtext);
        EditText text = (EditText)root.findViewById(R.id.contentstext);




        /*save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDBReference.child("/Commu_info/").get
                Commuinfo commuinfo = new Commuinfo(user.userId, )
            }
        });*/


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
