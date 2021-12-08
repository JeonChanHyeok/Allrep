package com.example.allrep.ui.community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.allrep.R;
import com.example.allrep.ui.community.fragment_commu.Fragment_community_list;
import com.example.allrep.ui.community.fragment_commu.Fragment_community_make;
import com.example.allrep.ui.community.fragment_commu.Fragment_community_text;

public class Community extends Fragment {

    Fragment_community_list fragment_community_list;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_community, container, false);
        fragment_community_list = new Fragment_community_list();
        Bundle bundle = new Bundle();
        bundle.getBundle("mycommu");
        fragment_community_list.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.community_container,fragment_community_list).commit();
        return root;
    }
}