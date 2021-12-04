package com.example.allrep.commuinfo;

import java.util.HashMap;
import java.util.Map;

public class Commuinfo {
    String userName;
    String textNo;
    String title;
    String text;
    String textDate;

    public Commuinfo(String userName, String textNo, String title, String text, String textDate) {
        this.userName = userName;
        this.textNo = textNo;
        this.title = title;
        this.text = text;
        this.textDate = textDate;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put(userName, "userName");
        result.put(textNo, "textNo");
        result.put(title, "title");
        result.put(text, "text");
        result.put(textDate, "textDate");
        return result;
    }

}
