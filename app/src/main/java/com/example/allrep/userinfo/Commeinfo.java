package com.example.allrep.userinfo;

public class Commeinfo {
    int textNo;
    String userId;
    String commetext;


    public Commeinfo(int textNo, String userId, String commetext){
        this.textNo = textNo;
        this.userId = userId;
        this.commetext = commetext;
    }

    public int getTextNo() {
        return textNo;
    }

    public String getUserId() {
        return userId;
    }

    public String getCommeText() {
        return commetext;
    }

    public void setTextNo(int textNo) {
        this.textNo = textNo;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCommetext(String commetext) {
        this.commetext = commetext;
    }
}
