package com.walletmix.maheromjan.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.walletmix.maheromjan.Database.RealmManager;
import com.walletmix.maheromjan.Database.SessionManager;
import com.walletmix.maheromjan.CountDownServices.IftarCountDownServices;
import com.walletmix.maheromjan.R;
import com.walletmix.maheromjan.CountDownServices.SehriCountDownServices;
import com.walletmix.maheromjan.Managers.SehriIftarPlusMinusManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsActivity extends AppCompatActivity {


    private Unbinder unbinder;

    @BindView(R.id.iftarAjan)
    SwitchCompat switchCompat;

    @BindView(R.id.jelaNameAtJElaPoribortonTv)
    TextView jelaNameTv;

    @BindView(R.id.jelaPoribortonLayout)
    LinearLayout jelaPoribortonLayout;
    SessionManager sessionManager;

    private int REQUEST_CODE = 13;
    private ActionBar actionBar;
    private RealmManager realmManager;
    private SehriIftarPlusMinusManager plusMinusManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        unbinder = ButterKnife.bind(this);
        init();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getString(R.string.settings));

        String distirct = SessionManager.getInstance(this).getString(SessionManager.Key.SELECTED_DISTRICT, getString(R.string.Manikganj));
        Boolean isAjanEnabled = sessionManager.getBoolean(SessionManager.Key.IS_IFTAR_AJAN_ENABLED, true);
        if (isAjanEnabled) {
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }
        jelaNameTv.setText(distirct);


        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sessionManager.put(SessionManager.Key.IS_IFTAR_AJAN_ENABLED, isChecked);
            }
        });


        jelaPoribortonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, SelectDistrictActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, REQUEST_CODE);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }

    private void init() {
        actionBar = getSupportActionBar();
        sessionManager = SessionManager.getInstance(this);
        realmManager = new RealmManager(this);
        plusMinusManager = new SehriIftarPlusMinusManager(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13) {
            try {
                String selectedDistrict = data.getStringExtra("dis");
                if (!selectedDistrict.isEmpty()) {
                    sessionManager.put(SessionManager.Key.SELECTED_DISTRICT, selectedDistrict);
                    jelaNameTv.setText(selectedDistrict);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
