package com.walletmix.maheromjan.Managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.walletmix.maheromjan.CountDownServices.AlertServices;
import com.walletmix.maheromjan.Database.SessionManager;
import com.walletmix.maheromjan.Receivers.DateChangeReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Siddhartha on 16/05/2018.
 */

public class TimeShceduleManager {
    private static AlarmManager alarmMgr;
    private static PendingIntent alarmIntent;
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy mm:HH");

    public static void setSchedule(Context context) {
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DateChangeReceiver.class);
        intent.setAction("com.walletmix.maheromjan.date_changed_custom");
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());


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
        int hour = Integer.parseInt(hourS);

        if (hour == 0) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
        SessionManager.getInstance(context).put(SessionManager.Key.SHCEDULED_STARTED, true);
    }

    public void cancelSchedule(Context context) {
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DateChangeReceiver.class);
        intent.setAction("com.walletmix.maheromjan.date_changed_custom");
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmMgr.cancel(alarmIntent);
    }
}
