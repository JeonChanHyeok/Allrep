package com.example.allrep.userinfo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
    public void update_feeding(){
        DatabaseReference mDBReference;
        mDBReference = FirebaseDatabase.getInstance().getReference();
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat get_year = new SimpleDateFormat("yyyyy", Locale.getDefault());
        SimpleDateFormat get_month = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat get_day = new SimpleDateFormat("dd", Locale.getDefault());

        int year = Integer.parseInt(get_year.format(time));
        int month = Integer.parseInt(get_month.format(time));
        int day = Integer.parseInt(get_day.format(time));
        for(int i=0; i < 10 ;i ++){
            Feedinginfo feedinginfo= new Feedinginfo(i, userName, animalName ,year, month, (day + i * feeding_day));
            mDBReference.child("/Feeding_time_info/").child(userName).child(animalName).child(Integer.toString(i)).setValue(feedinginfo);
        }
    }
}
