package com.example.allrep.commuinfo;

import java.util.HashMap;
import java.util.Map;

public class Commuinfo {
    String userName;
    int textNo;
    String title;
    String text;
    String textDate;

    public Commuinfo(){

    }

    public Commuinfo(String userName, int textNo, String title, String text, String textDate) {
        this.userName = userName;
        this.textNo = textNo;
        this.title = title;
        this.text = text;
        this.textDate = textDate;
    }



}
