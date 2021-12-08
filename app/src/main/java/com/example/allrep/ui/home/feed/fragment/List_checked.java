package com.example.allrep.ui.home.feed.fragment;

public class List_checked {
    public boolean checked;
    public int position;
    public List_checked(boolean b, int p){
        checked = b;
        position = p;
    }
    public boolean isChecked(){
        return checked;
    }
}
