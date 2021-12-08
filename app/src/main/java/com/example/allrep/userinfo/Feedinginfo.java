package com.example.allrep.userinfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Feedinginfo {
    public String userName;
    public String animalName;
    public int feeding_year;
    public int feeding_month;
    public int feeding_day;
    public int i;

    public Feedinginfo() {
    }

    public Feedinginfo(int i, String userName, String animalName, int feeding_year, int feeding_month, int feeding_day) {
        List<Integer> month = new ArrayList<>();
        month.add(1);
        month.add(3);
        month.add(5);
        month.add(7);
        month.add(8);
        month.add(10);
        month.add(12);
        if (month.contains(feeding_month)) {
            if(feeding_day > 31){
                feeding_day -= 31;
            }
        }else{
            if(feeding_day > 30){
                feeding_day -= 30;
            }
        }
        if(feeding_month > 12){
            feeding_month -= 12;
        }
        this.i = i;
        this.userName = userName;
        this.animalName = animalName;
        this.feeding_year = feeding_year;
        this.feeding_month = feeding_month;
        this.feeding_day = feeding_day;
    }
    public String getDatetoString(){
        return feeding_year + "년" + feeding_month + "월 " + feeding_day + "일";
    }
    public Date getDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
        String str = feeding_year + "-" + feeding_month + "-" + feeding_day;
        return format.parse(str);
    }

}
