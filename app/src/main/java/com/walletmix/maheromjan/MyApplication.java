package com.walletmix.maheromjan;

import android.app.Application;
import android.content.Intent;

import com.google.android.gms.ads.MobileAds;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.walletmix.maheromjan.Activity.NotificationDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Siddhartha on 13/05/2018.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        setUpOneSignal();
        setTupRealmDB();

    }


    public void setUpOneSignal() {
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler() {
                    @Override
                    public void notificationOpened(OSNotificationOpenResult result) {
                        JSONObject data = result.notification.payload.additionalData;
                        Intent intent = new Intent(getApplicationContext(), NotificationDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                        try {
                            intent.putExtra("title", data.getString("t"));
                            intent.putExtra("msg", data.getString("m"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        startActivity(intent);
                    }
                })
                .autoPromptLocation(true)
                .init();
    }


    public void setTupRealmDB() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("walletmix.maher_ramjan.1.2.realm").build();
        Realm.setDefaultConfiguration(config);
    }

}
