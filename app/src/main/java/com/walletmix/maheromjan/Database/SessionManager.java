package com.walletmix.maheromjan.Database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shishir on 14/05/2018.
 */

public class SessionManager {

    private static final String SETTINGS_NAME = "default_settings";
    private static SessionManager sessionManager;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private boolean mBulkUpdate = false;


    private SessionManager(Context context) {
        mPref = context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }

    public static SessionManager getInstance(Context context) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context.getApplicationContext());
        }
        return sessionManager;
    }


    //For String
    public void put(Key key, String val) {
        doEdit();
        mEditor.putString(key.name(), val);
        doCommit();
    }

    //For Int
    public void put(Key key, int val) {
        doEdit();
        mEditor.putInt(key.name(), val);
        doCommit();
    }

    //For Double
    public void put(Key key, double val) {
        doEdit();
        mEditor.putString(key.name(), String.valueOf(val));
        doCommit();
    }

    //For Bolean
    public void put(Key key, boolean val) {
        doEdit();
        mEditor.putBoolean(key.name(), val);
        doCommit();
    }
    public String getString(Key key, String defaultValue) {
        return mPref.getString(key.name(), defaultValue);
    }

    public String getString(Key key) {
        return mPref.getString(key.name(), null);
    }

    public int getInt(Key key) {
        return mPref.getInt(key.name(), 0);
    }

    public int getInt(Key key, int defaultValue) {
        return mPref.getInt(key.name(), defaultValue);
    }

    public boolean getBoolean(Key key, boolean defaultValue) {
        return mPref.getBoolean(key.name(), defaultValue);
    }

    public void edit() {
        mEditor = mPref.edit();
    }

    public void commit() {
        mEditor.commit();
        mEditor = null;
    }

    private void doEdit() {
        if (mEditor == null) {
            mEditor = mPref.edit();
        }
    }

    private void doCommit() {
        if (mEditor != null) {
            mEditor.commit();
            mEditor = null;
        }
    }

    public enum Key {
        IS_DATABASE_READY, SELECTED_DISTRICT, IS_IFTAR_AJAN_ENABLED, SEHRI_COUNT_DOWN_RUNNING, IFTAAR_COUTN_DOWN_RUNNING, RAMJAN_FINISHED, SHCEDULED_STARTED, IS_FIRST_TIME
    }

}

