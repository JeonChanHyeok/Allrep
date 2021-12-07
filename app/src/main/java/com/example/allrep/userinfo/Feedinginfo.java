package com.example.allrep.userinfo;

import java.util.Arrays;
import java.util.List;

public class Feedinginfo {
    public String userName;
    public String animalName;
    public int feeding_year;
    public int feeding_month;
    public int feeding_day;
    public int i;

    public Feedinginfo(int i, String userName, String animalName, int feeding_year, int feeding_month, int feeding_day) {
        List<Integer> month = Arrays.asList(new Integer[]{1,3,5,7,8,10,12});
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

}
