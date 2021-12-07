package com.example.allrep.ui.community.fragment_commu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.allrep.R;

public class Fragment_community_list extends Fragment {
    Fragment_community_make fragment_community_make;
    Fragment_community_text fragment_community_text;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_community_list_text, container, false);
        fragment_community_make = new Fragment_community_make();
        fragment_community_text = new Fragment_community_text();

        Button make_text = (Button)root.findViewById(R.id.newcomunity);
        Button combtn = (Button)root.findViewById(R.id.community_btn);

        combtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.community_container,fragment_community_text).commit();
            }
        });

        make_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.community_container,fragment_community_make).commit();
            }
        });

        return root;
    }
}
