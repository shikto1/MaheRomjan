package com.walletmix.maheromjan.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.walletmix.maheromjan.CountDownServices.AlertServices;
import com.walletmix.maheromjan.CountDownServices.SehriCountDownServices;
import com.walletmix.maheromjan.Database.RealmManager;
import com.walletmix.maheromjan.Database.SessionManager;
import com.walletmix.maheromjan.Managers.SehriIftarPlusMinusManager;

import java.util.Calendar;

/**
 * Created by Siddhartha on 15/05/2018.
 */

public class DateChangeReceiver extends BroadcastReceiver {

    private RealmManager realmManager;
    private SehriIftarPlusMinusManager plusMinusManager;


    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        if (year == 2018) {
            if ((month == 5 && day >= 17) || (month == 6 && day <= 16)) {
                realmManager = new RealmManager(context);
                plusMinusManager = new SehriIftarPlusMinusManager(context);
                SessionManager.getInstance(context).put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, true);
                SessionManager.getInstance(context).put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false);
                SessionManager.getInstance(context).put(SessionManager.Key.RAMJAN_FINISHED, false);
                int sehriTime = realmManager.getSehriTime();
                sehriTime = plusMinusManager.getSehriMinute(sehriTime);
                Intent countDownServiceIntent = new Intent(context, SehriCountDownServices.class);
                countDownServiceIntent.putExtra("min", sehriTime);
                countDownServiceIntent.putExtra("hour", 3);
                context.startService(countDownServiceIntent);
                // Ramjan running....
            } else if ((month == 6 && day >= 15) || month > 6) {
                SessionManager.getInstance(context).put(SessionManager.Key.RAMJAN_FINISHED, true);
            } else if (month == 5 && day < 17) {
                SessionManager.getInstance(context).put(SessionManager.Key.RAMJAN_FINISHED, true);
            }
        }
    }
}
