package com.walletmix.maheromjan.Database;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Shishir on 14/05/2018.
 */

public class RealmServices {

    private Realm realm = null;
    private Context context;

    public RealmServices(Context context) {
        this.context = context.getApplicationContext();
        try {
            realm = Realm.getDefaultInstance();
        } catch (Exception e) {

        }
    }

    public void addRow(SingleRow row){
        realm.beginTransaction();
        realm.copyToRealm(row);
        realm.commitTransaction();
    }


    public RealmResults<SingleRow> getAllRows() {
        RealmResults<SingleRow> allRows = realm.where(SingleRow.class).findAll();
        allRows.load();
        return allRows;
    }

    public int getSehriTime(String date){
        SingleRow singleRow = realm.where(SingleRow.class).equalTo("englishDate",date).findFirst();
        return singleRow.getSehriTime();
    }

    public int getIftarTime(String date){
        SingleRow singleRow = realm.where(SingleRow.class).equalTo("englishDate",date).findFirst();
        return singleRow.getIftarTime();
    }

    public void clearDatabase() {
        RealmResults<SingleRow> allRows = realm.where(SingleRow.class).findAll();
        realm.beginTransaction();
        allRows.deleteAllFromRealm();
        realm.commitTransaction();

    }

    public void closeRealm() {
        realm.close();
    }
}
