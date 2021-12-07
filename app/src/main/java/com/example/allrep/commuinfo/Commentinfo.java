package com.example.allrep.commuinfo;

import java.util.HashMap;
import java.util.Map;

public class Commentinfo {
    String userName;
    String textNo;
    String commutext;
    String commutextDate;

    public Commentinfo(String userName, String textNo, String commutext, String commutextDate) {
        this.userName = userName;
        this.textNo = textNo;
        this.commutext = commutext;
        this.commutextDate = commutextDate;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put(userName, "userName");
        result.put(textNo, "textNo");
        result.put(commutext, "commutext");
        result.put(commutextDate, "commutextDate");
        return result;
    }
}
