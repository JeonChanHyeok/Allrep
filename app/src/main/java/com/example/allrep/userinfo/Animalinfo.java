package com.example.allrep.userinfo;

import java.util.HashMap;
import java.util.Map;

public class Animalinfo {
    public String userName;
    public String animalName;
    public int animalAge;
    public String animalJong;
    public String animalImg;
    public int feeding_day; //피딩 주기
    public int feeding_gram;
    public int animalWheight;
    public String whatEat;

    public Animalinfo(){
    }

    public Animalinfo(String userName, String animalName, int animalAge, String animalJong, String animalImg, int feeding_day, int feeding_gram, int animalWheight, String whatEat) {
        this.userName = userName;
        this.animalName = animalName;
        this.animalAge = animalAge;
        this.animalJong = animalJong;
        this.animalImg = animalImg;
        this.feeding_day = feeding_day;
        this.feeding_gram = feeding_gram;
        this.animalWheight = animalWheight;
        this.whatEat = whatEat;
    }
}
