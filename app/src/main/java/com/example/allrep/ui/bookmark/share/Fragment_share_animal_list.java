package com.example.allrep.ui.bookmark.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.allrep.R;

public class Fragment_share_animal_list extends Fragment {
    Fragment_share_animal_info fragment_share_animal_info;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bookmark_list, container, false);
        Button share_btn = (Button)root.findViewById(R.id.bookmark_button);
        fragment_share_animal_info = new Fragment_share_animal_info();
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.bookmark_container,fragment_share_animal_info).commit();
            }
        });
        return root;
    }
}
