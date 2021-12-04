package com.example.allrep.userinfo;

import java.util.HashMap;
import java.util.Map;

public class Animalinfo {
    public String userName;
    public String animalName;
    public String animalAge;
    public String animalJong;
    public String animalImg;
    public int feeding_day;
    public int animalWheight;
    public String whatEat;

    public Animalinfo(){
    }

    public Animalinfo(String userName, String animalName, String animalAge, String animalJong, String animalImg, int feeding_day, int animalWheight, String whatEat) {
        this.userName = userName;
        this.animalName = animalName;
        this.animalAge = animalAge;
        this.animalJong = animalJong;
        this.animalImg = animalImg;
        this.feeding_day = feeding_day;
        this.animalWheight = animalWheight;
        this.whatEat = whatEat;
    }
}
