package com.example.allrep.userinfo;

import java.util.HashMap;
import java.util.Map;

public class Userinfo {
    public String userId;
    public String userPs;
    public int share;
    public int alram;

    public Userinfo(){
    }

    public Userinfo(String userId, String userPs){
        this.userId = userId;
        this.userPs = userPs;
        this.share = 0;
        this.alram = 0;
    }


    public Userinfo(String userId, String userPs, int share, int alram) {
        this.userId = userId;
        this.userPs = userPs;
        this.share = share;
        this.alram = alram;
    }
}
