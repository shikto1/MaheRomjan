package com.walletmix.maheromjan;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.walletmix.maheromjan.Activity.KuranTilaoatBanglaShohoActivity;
import com.walletmix.maheromjan.Activity.RomjanerHukumAhkamActivity;
import com.walletmix.maheromjan.Activity.RujarNiotIFtarerDuaActivity;
import com.walletmix.maheromjan.Activity.SehriIftarTimeActivity;
import com.walletmix.maheromjan.Activity.SelectDistrictActivity;
import com.walletmix.maheromjan.Activity.SettingsActivity;
import com.walletmix.maheromjan.Activity.ShobeKodorerFojilotActvity;
import com.walletmix.maheromjan.CountDownServices.AlertServices;
import com.walletmix.maheromjan.CountDownServices.IftarCountDownServices;
import com.walletmix.maheromjan.CountDownServices.SehriCountDownServices;
import com.walletmix.maheromjan.Database.RealmManager;
import com.walletmix.maheromjan.Database.SessionManager;
import com.walletmix.maheromjan.Database.SingleRow;
import com.walletmix.maheromjan.Managers.AdmobAddManager;
import com.walletmix.maheromjan.Managers.ConverToBanglaManager;
import com.walletmix.maheromjan.Managers.SehriIftarPlusMinusManager;
import com.walletmix.maheromjan.Managers.TimeShceduleManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Unbinder unbinder;

    @BindView(R.id.locationButtton)
    Button locationButton;

    @BindView(R.id.timerTv)
    TextView timerTv;

    @BindView(R.id.somoiBakiTv)
    TextView somoiBakiTv;

    @BindView(R.id.sehri_iftaar_er_dua_titleTv)
    TextView sehriIftarDuaTitleTv;

    @BindView(R.id.duaTv)
    TextView duaTv;

    @BindView(R.id.dividerFirst)
    View dividerFirst;

    @BindView(R.id.dividerSecond)
    View dividerSecond;

    @BindView(R.id.ajkerSehriIftarerSomoiTv)
    TextView ajkerSehriIftarerSomoiTv;

    long SECOND = 1000;
    long MIN = 60 * SECOND;
    long HOUR = 60 * MIN;

    RealmManager realmManager;
    SessionManager sessionManager;
    private String APP_LINK = "https://play.google.com/store/apps/details?id=" + "com.walletmix.maheromjan";
    private ShareDialog shareDialog;
    private SehriIftarPlusMinusManager plusMinusManager;
    private AdmobAddManager admobAddManager;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy mm:HH");
    private int REQUEST_CODE = 13;
    String TAG = "SHISHIR_13";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

        //Showing Add
        admobAddManager.showBannerToActivity(this, R.id.adView);
        admobAddManager.showInterStitialAd(this);

        shareDialog = new ShareDialog(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RealmResults<SingleRow> data = realmManager.getAllRows();
        if (data.isEmpty()) {
            realmManager.setDhakaTime();
        }

        if (sessionManager.getBoolean(SessionManager.Key.IS_FIRST_TIME, true)) {
            sessionManager.put(SessionManager.Key.IS_FIRST_TIME, false);
            if (isRamjanRunning()) {
                TimeShceduleManager.setSchedule(getApplicationContext());
                sessionManager.put(SessionManager.Key.SHCEDULED_STARTED, true);
                sessionManager.put(SessionManager.Key.RAMJAN_FINISHED, false);

                int sehriTime = plusMinusManager.getSehriMinute(realmManager.getSehriTime());
                int iftarTime = plusMinusManager.getIftarMinute(realmManager.getIftarTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
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
                Log.v(TAG, "Formatted Date: " + formattedDate);
                Log.v(TAG, hourS + ":" + minute);
                // Getting Hour in 24 Format...
                if (hour == 0) {
                    sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false);
                    sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false);
                } else if (hour >= 1 && hour <= 3) {
                    if (hour == 3 && minute < sehriTime) {
                        startSehriCountDownServices();
                    } else if (hour == 3 && minute > sehriTime) {
                        startIftarCountDownServices();
                    } else if (hour < 3) {
                        startSehriCountDownServices();
                    }
                } else if (hour > 3 && hour <= 19) {
                    if (hour < 18) {
                        startIftarCountDownServices();
                    } else if (hour == 18 && minute < iftarTime) {
                        startIftarCountDownServices();
                    } else if (hour == 19 && minute < iftarTime) {
                        startIftarCountDownServices();
                    } else if ((hour == 18 && minute > iftarTime) || (hour == 19 && minute > iftarTime)) {
                        sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false);
                        sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false);
                    }
                } else if (hour > 19) {
                    sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false);
                    sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false);
                }
            } else {
                sessionManager.put(SessionManager.Key.RAMJAN_FINISHED, true);
            }
        }
    }

    private void init() {
        sessionManager = SessionManager.getInstance(this);
        realmManager = new RealmManager(this);
        plusMinusManager = new SehriIftarPlusMinusManager(this);
        admobAddManager = new AdmobAddManager();
        locationButton.setOnClickListener(this);
    }

    private void startSehriCountDownServices() {
        sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, true);
        sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false);
        sessionManager.put(SessionManager.Key.RAMJAN_FINISHED, false);
        int sehriTime = realmManager.getSehriTime();
        sehriTime = plusMinusManager.getSehriMinute(sehriTime);
        Intent countDownServiceIntent = new Intent(this, SehriCountDownServices.class);
        countDownServiceIntent.putExtra("min", sehriTime);
        countDownServiceIntent.putExtra("hour", 3);
        startService(countDownServiceIntent);
    }

    private void startIftarCountDownServices() {
        AlertServices.shoToast(this, "START_IFTAR_COUNT_D");
        sessionManager.put(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false);
        sessionManager.put(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, true);
        int iftarTime = realmManager.getIftarTime();
        iftarTime = plusMinusManager.getIftarMinute(iftarTime);
        Intent countDownServiceIntent = new Intent(getApplicationContext(), IftarCountDownServices.class);
        if (iftarTime >= 60) {
            int remainder = iftarTime - 60;
            countDownServiceIntent.putExtra("hour", 7 + 12);
            countDownServiceIntent.putExtra("min", remainder);
        } else {
            countDownServiceIntent.putExtra("hour", 6 + 12);
            countDownServiceIntent.putExtra("min", iftarTime);
        }
        startService(countDownServiceIntent);
    }


    private boolean isRamjanRunning() {
        boolean result = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        Log.v(TAG, day + ":" + month + ":" + year);
        if (year == 2018) {
            if ((month == 5 && day >= 16) || (month == 6 && day <= 15)) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationButton.setText(SessionManager.getInstance(this).getString(SessionManager.Key.SELECTED_DISTRICT));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("send_info"));
        //registerReceiver(receiver,new IntentFilter("send_info"));
        if (sessionManager.getBoolean(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false)) {  // SEHREE Couont down running..
            Log.v(TAG, "SEHRI_COUNTDOWN_RUNNING");
            somoiBakiTv.setText(getString(R.string.sehrirSomoiBaki));
            sehriIftarDuaTitleTv.setText(getString(R.string.rujarNiotTitle));
            ajkerSehriIftarerSomoiTv.setText(getString(R.string.ajkerSehrirShesSomoi) + ": " + plusMinusManager.getSehri(realmManager.getSehriTime()));
            duaTv.setText(getString(R.string.sehrirDuaBangla));
        } else if (sessionManager.getBoolean(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false)) {
            Log.v(TAG, "IFTAR_COUNTDOWN_RUNNING");// IFTAAR Count down running...
            somoiBakiTv.setText(getString(R.string.iftarerSomoiBaki));
            ajkerSehriIftarerSomoiTv.setText(getString(R.string.ajkerIftarerSomoi) + ": " + ConverToBanglaManager.getInBangla(plusMinusManager.getIftar(realmManager.getIftarTime())));
            sehriIftarDuaTitleTv.setText(getString(R.string.iftarirDuaTitle));
            duaTv.setText(getString(R.string.iftarerduaBangla));
        } else if (!sessionManager.getBoolean(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false)
                && !sessionManager.getBoolean(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false)
                && !sessionManager.getBoolean(SessionManager.Key.RAMJAN_FINISHED, false)) {
            setUpAfterIftarData();
        } else if (sessionManager.getBoolean(SessionManager.Key.RAMJAN_FINISHED, false)) {  // Ramjan Finished....
            Log.v(TAG, "RAMJAN FINISHED");
            sehriIftarDuaTitleTv.setText(getString(R.string.hadisTitleOne));
            duaTv.setText(getString(R.string.banglaHadishOne));
            ajkerSehriIftarerSomoiTv.setText("");
            dividerFirst.setVisibility(View.INVISIBLE);
            dividerSecond.setVisibility(View.INVISIBLE);
            somoiBakiTv.setVisibility(View.INVISIBLE);
            timerTv.setVisibility(View.INVISIBLE);
        }
    }

    private void setUpAfterIftarData() {
        somoiBakiTv.setText(getString(R.string.ajkerSehrirShesSomoi));
        String sehriTime = plusMinusManager.getSehri(realmManager.getSehriTime());
        timerTv.setText(ConverToBanglaManager.getInBangla(sehriTime));
        ajkerSehriIftarerSomoiTv.setText(getString(R.string.rateOneTarPorCountDownShuruhobe));
        sehriIftarDuaTitleTv.setText(getString(R.string.rujarNiotTitle));
        duaTv.setText(getString(R.string.sehrirDuaBangla));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        // unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        realmManager.closeRealmServices();
        if (!sessionManager.getBoolean(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false) && !sessionManager.getBoolean(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false)) {
            if (isMyServiceRunning(IftarCountDownServices.class)) {
                stopService(new Intent(this, IftarCountDownServices.class));
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String timee = intent.getStringExtra("time");
                boolean isIFtarTimeFinished = intent.getBooleanExtra("isIftarTimeFinished", false);
                boolean isSehriFinished = intent.getBooleanExtra("isSehriFinished", false);
                String from = intent.getStringExtra("from");
                if (from.equals("IFTAR")) {
                    if (sessionManager.getBoolean(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false)) {
                        // AlertServices.shoToast(MainActivity.this,"IFTAR_C_RUNNING");
                        somoiBakiTv.setText(getString(R.string.iftarerSomoiBaki));
                        String time = intent.getStringExtra("time");
                        timerTv.setText(time);
                        // timerTv.setText(ConverToBanglaManager.getInBangla(time));
                    } else if (isIFtarTimeFinished) {
                        Log.v(TAG, "IFTAR_FINISHED");
                        //AlertServices.shoToast(MainActivity.this,"IFTAR_FINISHED");
                        somoiBakiTv.setText(getString(R.string.ajkerSehrirShesSomoi));
                        String sehriTime = plusMinusManager.getSehri(realmManager.getSehriTime());
                        timerTv.setText(ConverToBanglaManager.getInBangla(sehriTime));
                        ajkerSehriIftarerSomoiTv.setText(getString(R.string.rateOneTarPorCountDownShuruhobe));
                        sehriIftarDuaTitleTv.setText(getString(R.string.rujarNiotTitle));
                        duaTv.setText(getString(R.string.sehrirDuaBangla));

                    }
                } else {
                    if (sessionManager.getBoolean(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false)) {
                        somoiBakiTv.setText(getString(R.string.sehrirSomoiBaki));
                        // AlertServices.shoToast(MainActivity.this,"SEHRI_C_RUNNING");
                        String time = intent.getStringExtra("time");
                        //  timerTv.setText(ConverToBanglaManager.getInBangla(time));
                        timerTv.setText(time);
                        ajkerSehriIftarerSomoiTv.setText(getString(R.string.ajkerSehrirShesSomoi) + ": " + ConverToBanglaManager.getInBangla(plusMinusManager.getSehri(realmManager.getSehriTime())));
                        sehriIftarDuaTitleTv.setText(getString(R.string.rujarNiotTitle));
                        duaTv.setText(getString(R.string.sehrirDuaBangla));
                    } else if (isSehriFinished) {
                        // AlertServices.shoToast(MainActivity.this,"SEHRI _FINISHED");
                        somoiBakiTv.setText(getString(R.string.iftarerSomoiBaki));
                        ajkerSehriIftarerSomoiTv.setText(getString(R.string.ajkerIftarerSomoi) + ": " + ConverToBanglaManager.getInBangla(plusMinusManager.getIftar(realmManager.getIftarTime())));
                        sehriIftarDuaTitleTv.setText(getString(R.string.iftarirDuaTitle));
                        duaTv.setText(getString(R.string.iftarerduaBangla));
                    }
                }
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = new Intent(this, RomjanerHukumAhkamActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (id == R.id.sehriOiftarerSomoi) {
            Intent intent1 = new Intent(MainActivity.this, SehriIftarTimeActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else if (id == R.id.rujaVongherKaron) {
            intent.putExtra("menu", AllKey.RUJA_VONGER_KARON);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else if (id == R.id.rujaVongherKaronNoi) {
            intent.putExtra("menu", AllKey.RUJA_VONGER_KARON_NOI);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else if (id == R.id.romjanerFojilot) {
            intent.putExtra("menu", AllKey.MAHE_RAMJANER_FAJILOT);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else if (id == R.id.shobeKodorerFojilot) {
            startActivity(new Intent(this, ShobeKodorerFojilotActvity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else if (id == R.id.rujarNiotEftarerDua) {
            startActivity(new Intent(this, RujarNiotIFtarerDuaActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else if (id == R.id.share_in_facebook) {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setQuote("This is a useful app for Mahe Romjan 2018")
                    .setContentUrl(Uri.parse(APP_LINK))
                    .build();
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                shareDialog.show(content);
            } else {
                shareAppLinkViaFacebookWithoutSDK();
            }
        } else if (id == R.id.rateUS) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id == R.id.suraYasingBanglaSoho) {
            Intent in = new Intent(this, KuranTilaoatBanglaShohoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void shareAppLinkViaFacebookWithoutSDK() {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Intent intent1 = new Intent();
            intent1.setPackage("com.facebook.katana");
            intent1.setAction("android.intent.action.SEND");
            intent1.setType("text/plain");
            intent1.putExtra("android.intent.extra.TEXT", APP_LINK);
            startActivity(intent1);
        } catch (Exception e) {
            // If we failed (not native FB app installed), try share through SEND
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + APP_LINK;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
            startActivity(intent);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SelectDistrictActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, REQUEST_CODE);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13) {
            try {
                String selectedDistrict = data.getStringExtra("dis");
                if (!selectedDistrict.isEmpty()) {
                    sessionManager.put(SessionManager.Key.SELECTED_DISTRICT, selectedDistrict);
                    locationButton.setText(selectedDistrict);
                    if (sessionManager.getBoolean(SessionManager.Key.SEHRI_COUNT_DOWN_RUNNING, false)) {
                        //Restrat sehri count down services with new time
                        int sehriTime = realmManager.getSehriTime();
                        sehriTime = plusMinusManager.getSehriMinute(sehriTime);
                        Intent countDownServiceIntent = new Intent(this, SehriCountDownServices.class);
                        countDownServiceIntent.putExtra("min", sehriTime);
                        countDownServiceIntent.putExtra("hour", 3);
                        stopService(new Intent(this, SehriCountDownServices.class));
                        startService(countDownServiceIntent);
                    } else if (sessionManager.getBoolean(SessionManager.Key.IFTAAR_COUTN_DOWN_RUNNING, false)) {
                        //Restart iftar count down services with time
                        int iftarTime = realmManager.getIftarTime();
                        iftarTime = plusMinusManager.getIftarMinute(iftarTime);
                        Intent countDownServiceIntent = new Intent(this, IftarCountDownServices.class);
                        if (iftarTime >= 60) {
                            int remainder = iftarTime - 60;
                            countDownServiceIntent.putExtra("hour", 7 + 12);
                            countDownServiceIntent.putExtra("min", remainder);
                        } else {
                            countDownServiceIntent.putExtra("hour", 6 + 12);
                            countDownServiceIntent.putExtra("min", iftarTime);
                        }
                        stopService(new Intent(this, IftarCountDownServices.class));
                        startService(countDownServiceIntent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
