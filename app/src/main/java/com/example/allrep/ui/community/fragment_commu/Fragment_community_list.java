package com.example.allrep.ui.community.fragment_commu;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
//import android.widget.SearchView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.allrep.LoginViewModel;
import com.example.allrep.R;
import com.example.allrep.commuinfo.Commuinfo;
import com.example.allrep.userinfo.Userinfo;
import com.example.allrep.userinfo.communityinfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_community_list extends Fragment {
    Fragment_community_make fragment_community_make;
    Fragment_community_text fragment_community_text;
    Fragment_community_list fragment_community_list;
    DatabaseReference mDBReference = null;
    private LoginViewModel loginViewModel;
    Userinfo user;
    List<Commuinfo> text_list;
    Button newcomunitybtn;
    Button mycomunitybtn;
    // Button combtn; 실험 버튼 삭제
    ListView listView;
    Listitemadapter adapter;
    int mycomm = 1;

    // 찾기 버튼 searchbutton  찾기 텍스트 search_view
    Button searchbutton;
    SearchView search_view;
    String searchdata;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_community_list_text, container, false);
        fragment_community_make = new Fragment_community_make();
        fragment_community_text = new Fragment_community_text();
        fragment_community_list = new Fragment_community_list();

        // 번들 획득
        mycomm = getArguments().getInt("mycommu", 1);
        searchdata = getArguments().getString("searchbundle");

        // 생성버튼변수 newcomunity 내글찾기변수 mycomunity
        newcomunitybtn = (Button)root.findViewById(R.id.newcomunity);
        mycomunitybtn = (Button)root.findViewById(R.id.mycomunity);
        searchbutton = (Button)root.findViewById(R.id.searchbutton);
        search_view = (SearchView)root.findViewById(R.id.search_view);

        listView = root.findViewById(R.id.community_listview);
        adapter = new Listitemadapter();
        mDBReference = FirebaseDatabase.getInstance().getReference();


        System.out.println(mycomm);

        // 자신의 글 보기
        if (mycomm == 0){
            mycomunitybtn.setText("전체글보기");
            mDBReference.child("/community_info/").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String key = snapshot.getKey();
                        HashMap<String, Object> writedata = (HashMap<String, Object>) data.getValue();
                        //System.out.println(writedata);
                        String[] getData = {writedata.get("number").toString(), writedata.get("userName").toString(),
                                            writedata.get("wirtetitle").toString()};
                        //System.out.println(getData);
                        String number = getData[0];
                        int numberA = Integer.parseInt(number);
                        String name = getData[1];
                        String title = getData[2];
                        if (user.userId.equals(name)){
                            adapter.addItem(numberA, name, title);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        // 전체 글 보기
        else if (mycomm == 1) {
            mycomunitybtn.setText("내글보기");
            mDBReference.child("/community_info/").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String key = snapshot.getKey();
                        HashMap<String, Object> writedata = (HashMap<String, Object>) data.getValue();
                        //System.out.println(writedata);
                        String[] getData = {writedata.get("number").toString(), writedata.get("userName").toString(),
                                            writedata.get("wirtetitle").toString()};
                        //System.out.println(getData);
                        String number = getData[0];
                        int numberA = Integer.parseInt(number);
                        String name = getData[1];
                        String title = getData[2];
                        adapter.addItem(numberA, name, title);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        // 검색 글 보기
        else if (mycomm == 2) {
            // 찾기 버튼 searchbutton  찾기 텍스트 search_view
            // 검색부분
            mDBReference.child("/community_info/").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String key = snapshot.getKey();
                        HashMap<String, Object> writedata = (HashMap<String, Object>) data.getValue();
                        //System.out.println(writedata);
                        String[] getData = {writedata.get("number").toString(), writedata.get("userName").toString(),
                                            writedata.get("wirtetitle").toString()};
                        //System.out.println(getData);
                        String number = getData[0];
                        int numberA = Integer.parseInt(number);
                        String name = getData[1];
                        String title = getData[2];
                        if (title.contains(searchdata)) {
                            adapter.addItem(numberA, name, title);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //리스트뷰를 클릭하면 번들로 넘기는부분
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int thrownumber = adapter.item.get(position).number;
                Bundle bundle = new Bundle();
                bundle.putInt("thrownumber",thrownumber);
                fragment_community_text.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.community_container,fragment_community_text).commit();
            }
        });

        //글작성
        newcomunitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.community_container,fragment_community_make).commit();
            }
        });

        // 내글보기 전체글보기
        mycomunitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if (mycomm == 1){
                    mycomm = 0;
                }
                else {
                    mycomm = 1;
                }
                bundle.putInt("mycommu", mycomm);
                fragment_community_list.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.community_container,fragment_community_list).commit();
            }
        });

        // 검색부분 서치부분 + 바뀌는 번들부분 넘기기
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                searchdata = search_view.getQuery().toString();
                mycomm = 2;
                bundle.putInt("mycommu", mycomm);
                bundle.putString("searchbundle" ,searchdata);
                fragment_community_list.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.community_container,fragment_community_list).commit();
            }
        });

        listView.setAdapter(adapter);
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
                newcomunitybtn.setEnabled(true);
                mycomunitybtn.setEnabled(true);
            }
        });
    }

    public class Listitemadapter extends BaseAdapter{
        ArrayList<communityinfo> item = new ArrayList<communityinfo>();
        Context context;

        @Override
        public int getCount() {
            return item.size();
        }

        @Override
        public Object getItem(int position) {
            return item.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Context context = parent.getContext();

            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_community_listitem,parent,false);
            }
            // 번호 : writenumberA 제목 : writetitleA 작성자 : writerA
            TextView number = (TextView) convertView.findViewById(R.id.writenumberA);
            TextView title = (TextView) convertView.findViewById(R.id.writetitleA);
            TextView writer = (TextView) convertView.findViewById(R.id.writerA);

            communityinfo items = (communityinfo) getItem(position);

            number.setText(Integer.toString(items.getNumber()));
            title.setText(items.getUserName());
            writer.setText(items.getWirtetitle());

            return convertView;
        }

        public void addItem(int number, String title, String writer){
            communityinfo mItem = new communityinfo();

            mItem.setNumber(number);
            mItem.setWirtetitle(title);
            mItem.setUserName(writer);

            item.add(mItem);
        }
    }
}
