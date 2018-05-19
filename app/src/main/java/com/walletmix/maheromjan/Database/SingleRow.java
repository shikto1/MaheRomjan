package com.walletmix.maheromjan.Database;

import io.realm.RealmObject;

/**
 * Created by Shishir on 14/05/2018.
 */

public class SingleRow extends RealmObject{
    private int arabicDate;
    private String englishDate;
    private String day;
    private int sehriTime;
    private int iftarTime;

    public SingleRow(){

    }

    public SingleRow(int arabicDate, String englishDate, String day, int sehriTime, int iftarTime) {
        this.arabicDate = arabicDate;
        this.englishDate = englishDate;
        this.day = day;
        this.sehriTime = sehriTime;
        this.iftarTime = iftarTime;
    }

    public String getEnglishDate() {
        return englishDate;
    }


    public String getDay() {
        return day;
    }


    public int getSehriTime() {
        return sehriTime;
    }


    public int getIftarTime() {
        return iftarTime;
    }

}
