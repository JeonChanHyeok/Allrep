package com.example.allrep.userinfo;

public class communityinfo {

    public String wirtetitle;
    public String writecontent;
    public String userName;
    public int number;

    public communityinfo(){
    }

    public communityinfo(String wirtetitle, String writecontent, String userName, int number){
        this.wirtetitle = wirtetitle;
        this.writecontent = writecontent;
        this.userName = userName;
        this.number = number;
    }

    public String getWirtetitle() {
        return wirtetitle;
    }

    public String getWritecontent() {
        return writecontent;
    }

    public String getUserName() {
        return userName;
    }

    public int getNumber() {
        return number;
    }

    public void setWirtetitle(String wirtetitle) {
        this.wirtetitle = wirtetitle;
    }

    public void setWritecontent(String writecontent) {
        this.writecontent = writecontent;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
