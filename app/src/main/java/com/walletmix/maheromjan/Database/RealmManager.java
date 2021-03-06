package com.walletmix.maheromjan.Database;

import android.content.Context;

import com.walletmix.maheromjan.AllDay;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmResults;

/**
 * Created by Shishir on 14/05/2018.
 */

public class RealmManager {
    RealmServices realmServices;
    Context context;
    private String SEHRI_HOUR = "03";
    private String IFTAR_HOUR = "06";
    Date date;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    String today;

    public RealmManager(Context context) {
        realmServices = new RealmServices(context);
        this.context = context;
        date = new Date();
        today = formatter.format(date);
    }


    //SAVING TIME FOR DHAKA.................
    public void setDhakaTime() {
        realmServices.addRow(new SingleRow(1, "18-05-2018", AllDay.FRI, 46, 39));
        realmServices.addRow(new SingleRow(2, "19-05-2018", AllDay.SAT, 45, 39));
        realmServices.addRow(new SingleRow(3, "20-05-2018", AllDay.SUN, 44, 40));
        realmServices.addRow(new SingleRow(4, "21-05-2018", AllDay.MON, 44, 40));
        realmServices.addRow(new SingleRow(5, "22-05-2018", AllDay.TUE, 43, 41));
        realmServices.addRow(new SingleRow(6, "23-05-2018", AllDay.WED, 43, 42));
        realmServices.addRow(new SingleRow(7, "24-05-2018", AllDay.THU, 42, 42));
        realmServices.addRow(new SingleRow(8, "25-05-2018", AllDay.FRI, 42, 42));
        realmServices.addRow(new SingleRow(90, "26-05-2018", AllDay.SAT, 41, 43));
        realmServices.addRow(new SingleRow(10, "27-05-2018", AllDay.SUN, 41, 43));
        realmServices.addRow(new SingleRow(11, "28-05-2018", AllDay.MON, 40, 44));
        realmServices.addRow(new SingleRow(12, "29-05-2018", AllDay.TUE, 40, 44));
        realmServices.addRow(new SingleRow(13, "30-05-2018", AllDay.WED, 40, 45));
        realmServices.addRow(new SingleRow(14, "31-05-2018", AllDay.THU, 39, 45));
        realmServices.addRow(new SingleRow(15, "01-06-2018", AllDay.FRI, 39, 46));
        realmServices.addRow(new SingleRow(16, "02-06-2018", AllDay.SAT, 39, 46));
        realmServices.addRow(new SingleRow(17, "03-06-2018", AllDay.SUN, 39, 46));
        realmServices.addRow(new SingleRow(18, "04-06-2018", AllDay.MON, 39, 47));
        realmServices.addRow(new SingleRow(29, "05-06-2018", AllDay.TUE, 39, 47));
        realmServices.addRow(new SingleRow(20, "06-06-2018", AllDay.WED, 38, 47));
        realmServices.addRow(new SingleRow(21, "07-06-2018", AllDay.THU, 38, 48));
        realmServices.addRow(new SingleRow(22, "08-06-2018", AllDay.FRI, 38, 48));
        realmServices.addRow(new SingleRow(23, "09-06-2018", AllDay.SAT, 38, 49));
        realmServices.addRow(new SingleRow(24, "10-06-2018", AllDay.SUN, 38, 49));
        realmServices.addRow(new SingleRow(25, "11-06-2018", AllDay.MON, 38, 50));
        realmServices.addRow(new SingleRow(26, "12-06-2018", AllDay.TUE, 38, 50));
        realmServices.addRow(new SingleRow(27, "13-06-2018", AllDay.WED, 37, 50));
        realmServices.addRow(new SingleRow(28, "14-06-2018", AllDay.THU, 38, 50));
        realmServices.addRow(new SingleRow(39, "15-06-2018", AllDay.FRI, 38, 51));
        realmServices.addRow(new SingleRow(30, "16-06-2018", AllDay.SAT, 38, 51));
    }

    public RealmResults<SingleRow> getAllRows() {
       return realmServices.getAllRows();
    }

    public int getSehriTime(){
        return realmServices.getSehriTime(today);
    }

    public int getIftarTime(){
        return realmServices.getIftarTime(today);
    }


    public void closeRealmServices() {
        realmServices.closeRealm();
    }

}
