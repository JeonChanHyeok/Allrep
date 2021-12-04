package com.example.allrep.userinfo;

import java.util.HashMap;
import java.util.Map;

public class Feedinginfo {
    String userName;
    String animalName;
    String animalImg;
    String feedingDate;

    public Feedinginfo(String userName, String animalName, String animalImg, String feedingDate) {
        this.userName = userName;
        this.animalName = animalName;
        this.animalImg = animalImg;
        this.feedingDate = feedingDate;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put(userName, "userName");
        result.put(animalName, "animalName");
        result.put(animalImg, "animalImg");
        result.put(feedingDate, "feedingDate");
        return result;
    }
}
