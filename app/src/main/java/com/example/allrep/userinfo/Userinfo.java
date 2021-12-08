package com.example.allrep.userinfo;

import java.util.HashMap;
import java.util.Map;

public class Userinfo {
    public String userId;
    public String userPs;
    public int share; // 공유 제한
    public int alram; // 알람 여부
    public String token;

    public Userinfo(){
    }

    public Userinfo(String userId, String userPs, String token){
        this.userId = userId;
        this.userPs = userPs;
        this.share = 0;
        this.alram = 0;
        this.token = token;
    }


    public Userinfo(String userId, String userPs, int share, int alram, String token) {
        this.userId = userId;
        this.userPs = userPs;
        this.share = share;
        this.alram = alram;
        this.token = token;
    }
}
