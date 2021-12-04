package com.example.allrep.ui.bookmark;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.allrep.R;
import com.example.allrep.ui.bookmark.share.Fragment_share_animal_info;
import com.example.allrep.ui.bookmark.share.Fragment_share_animal_list;

public class Bookmark extends Fragment {
    Fragment_share_animal_info fragment_share_animal_info;
    Fragment_share_animal_list fragment_share_animal_list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_bookmark, container, false);
        Button share_btn = (Button)root.findViewById(R.id.share_btn);
        fragment_share_animal_list = new Fragment_share_animal_list();
        fragment_share_animal_info = new Fragment_share_animal_info();
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.bookmark_container,fragment_share_animal_list).commit();
            }
        });




        return root;
    }
}