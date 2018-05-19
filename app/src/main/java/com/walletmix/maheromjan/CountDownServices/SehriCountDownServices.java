package com.walletmix.maheromjan.CountDownServices;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.walletmix.maheromjan.Database.RealmManager;
import com.walletmix.maheromjan.Database.SessionManager;
import com.walletmix.maheromjan.Managers.SehriIftarPlusMinusManager;

import java.util.Calendar;

/**
 * Created by Siddhartha on 17/05/2018.
 */

public class SehriCountDownServices extends Service {
    long SECOND = 1000;
    long MIN = 60 * SECOND;
    long HOUR = 60 * MIN;
    private CountDownTimer countDownTimer;
    private SessionManager sessionManager;
    private RealmManager realmManager;
    private SehriIftarPlusMinusManager plusMinusManager;


    @Override
    public void onCreate() {
        super.onCreate();
        sessionManager = SessionManager.getInstance(this);
        realmManager = new RealmManager(this);
        plusMinusManager = new SehriIftarPlusMinusManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int min = intent.getIntExtra("min", 0);
            int hour = intent.getIntExtra("hour", 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, min);
            long mills = calendar.getTimeInMillis() - System.currentTimeMillis();
            startCountDown(mills);
        }
        return START_REDELIVER_INTENT;
    }


    private void startCountDown(long remainingTimeInMill) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(remainingTimeInMill, SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                int min = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                String text = (hours < 10 ? "0" + hours : hours) + ":" + (min < 10 ? "0" + min : min) + ":" + (seconds < 10 ? "0" + seconds : seconds);
                sendBroadcastMessage(text, false, false);
            }

            @Override
            public void onFinish() {
                sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false);
                sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, true);
                int iftarTime = realmManager.getIftarTime();
                iftarTime = plusMinusManager.getIftarMinute(iftarTime);
                Intent countDownServiceIntent = new Intent(SehriCountDownServices.this, IftarCountDownServices.class);
                if (iftarTime >= 60) {
                    int remainder = iftarTime - 60;
                    countDownServiceIntent.putExtra("hour", 7 + 12);
                    countDownServiceIntent.putExtra("min", remainder);
                } else {
                    countDownServiceIntent.putExtra("hour", 6 + 12);
                    countDownServiceIntent.putExtra("min", iftarTime);
                }
                sendBroadcastMessage("00:00:00", false, true);
                startService(countDownServiceIntent);
                stopSelf();

            }
        }.start();
    }


    private void sendBroadcastMessage(String text, boolean isIftarFinish, boolean isSehriFinished) {
        Intent intent = new Intent("send_info");
        intent.putExtra("from", "SEHRI");
        intent.putExtra("time", text);
        intent.putExtra("isIftarTimeFinished", isIftarFinish);
        intent.putExtra("isSehriFinished", isSehriFinished);
        intent.putExtra("from", "SEHRI");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        // sendBroadcast(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
