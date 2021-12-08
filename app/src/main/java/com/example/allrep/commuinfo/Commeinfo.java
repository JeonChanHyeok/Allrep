package com.example.allrep.commuinfo;

public class Commeinfo {
    public int number;
    public int num;
    public String userId;
    public String commetext;


    public Commeinfo(int number, int num, String userId, String commetext){
        this.number = number;
        this.num = num;
        this.userId = userId;
        this.commetext = commetext;
    }

    public Commeinfo(){
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommetext() {
        return commetext;
    }

    public void setCommetext(String commetext) {
        this.commetext = commetext;
    }
}
