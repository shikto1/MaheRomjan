package com.walletmix.maheromjan.Receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.walletmix.maheromjan.CountDownServices.IftarCountDownServices;
import com.walletmix.maheromjan.CountDownServices.SehriCountDownServices;
import com.walletmix.maheromjan.Database.RealmManager;
import com.walletmix.maheromjan.Database.SessionManager;
import com.walletmix.maheromjan.Managers.SehriIftarPlusMinusManager;
import com.walletmix.maheromjan.Managers.TimeShceduleManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Siddhartha on 16/05/2018.
 */

public class DeviceBootReceiver extends BroadcastReceiver {

    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;
    private SessionManager sessionManager;
    private SehriIftarPlusMinusManager plusMinusManager;
    private RealmManager realmManager;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy mm:HH");

    @Override
    public void onReceive(Context context, Intent in) {
        init(context);
        if (isRamjanRunning()) {
            TimeShceduleManager.setSchedule(context);
            sessionManager.put(SessionManager.Key.SHCEDULED_STARTED, true);
            sessionManager.put(SessionManager.Key.RAMJAN_FINISHED, false);
            int sehriTime = plusMinusManager.getSehriMinute(realmManager.getSehriTime());
            int iftarTime = plusMinusManager.getIftarMinute(realmManager.getIftarTime());
            Calendar calendar = Calendar.getInstance();

            // Getting Hour in 24 Format...
            String formattedDate = formatter.format(new Date());
            int length = formattedDate.length();
            String hourS = "";
            boolean coloFound = false;
            for (int j = 0; j < length; j++) {
                char ch = formattedDate.charAt(j);
                if (ch == ':') {
                    coloFound = true;
                } else {
                    if (coloFound) {
                        hourS += ch;
                    }
                }
            }
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int year = calendar.get(Calendar.YEAR);
            int minute = calendar.get(Calendar.MINUTE);
            int hour = Integer.parseInt(hourS);

            // Getting Hour in 24 Format...
            if (hour == 0){
                sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING,false);
                sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING,false);
            } else if (hour >= 1 && hour <= 3) {
                if (hour == 3 && minute < sehriTime) {
                    startSehriCountDownServices(context);
                } else if (hour == 3 && minute > sehriTime) {
                    startIftarCountDownServices(context);
                } else if (hour < 3) {
                    startSehriCountDownServices(context);
                }
            } else if (hour > 3 && hour <= 19) {
                if (hour < 18) {
                    startIftarCountDownServices(context);
                } else if (hour == 18 && minute < iftarTime) {
                    startIftarCountDownServices(context);
                } else if (hour == 19 && minute < iftarTime) {
                    startIftarCountDownServices(context);
                } else if ((hour == 18 && minute > iftarTime) || (hour == 19 && minute > iftarTime)) {
                    sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false);
                    sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false);
                }
            }else if (hour > 19){
                sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING,false);
                sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING,false);
            }
        }else{
            sessionManager.put(SessionManager.Key.RAMJAN_FINISHED,true);
        }
    }

    private void init(Context context) {
        sessionManager = SessionManager.getInstance(context);
        realmManager = new RealmManager(context);
        plusMinusManager = new SehriIftarPlusMinusManager(context);
    }

    private boolean isRamjanRunning() {
        boolean result = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        if (year == 2018) {
            if ((month == 5 && day >= 16) || (month == 6 && day <= 15)) {
                result = true;
            }
        }
        return result;
    }


    private void startSehriCountDownServices(Context context) {
        sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, true);
        sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false);
        sessionManager.put(SessionManager.Key.RAMJAN_FINISHED, false);
        int sehriTime = realmManager.getSehriTime();
        sehriTime = plusMinusManager.getSehriMinute(sehriTime);
        Intent countDownServiceIntent = new Intent(context, SehriCountDownServices.class);
        countDownServiceIntent.putExtra("min", sehriTime);
        countDownServiceIntent.putExtra("hour", 3);
        context.startService(countDownServiceIntent);
    }

    private void startIftarCountDownServices(Context context) {
        sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false);
        sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, true);
        int iftarTime = realmManager.getIftarTime();
        iftarTime = plusMinusManager.getIftarMinute(iftarTime);
        Intent countDownServiceIntent = new Intent(context, IftarCountDownServices.class);
        if (iftarTime >= 60) {
            int remainder = iftarTime - 60;
            countDownServiceIntent.putExtra("hour", 7 + 12);
            countDownServiceIntent.putExtra("min", remainder);
        } else {
            countDownServiceIntent.putExtra("hour", 6 + 12);
            countDownServiceIntent.putExtra("min", iftarTime);
        }
        context.startService(countDownServiceIntent);
    }


}
