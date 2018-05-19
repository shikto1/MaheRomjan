package com.walletmix.maheromjan.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.walletmix.maheromjan.Adapter.SehriIftarTimeAdapter;
import com.walletmix.maheromjan.Managers.AdmobAddManager;
import com.walletmix.maheromjan.Database.RealmManager;
import com.walletmix.maheromjan.Database.SessionManager;
import com.walletmix.maheromjan.Database.SingleRow;
import com.walletmix.maheromjan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.RealmResults;

public class SehriIftarTimeActivity extends AppCompatActivity {



    private RealmManager realmManager;
    private SehriIftarTimeAdapter sehriIftarTimeAdapter;
    private Unbinder unbinder;


    @BindView(R.id.sehri_iftar_timeListView)
    ListView sehriIftarTimeListView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sehri_iftar_time);
        unbinder = ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        String district = SessionManager.getInstance(this).getString(SessionManager.Key.SELECTED_DISTRICT);
        actionBar.setTitle(district + " জেলা");

        realmManager = new RealmManager(this);
        RealmResults<SingleRow> allData = realmManager.getAllRows();
        if (allData != null){
            sehriIftarTimeAdapter = new SehriIftarTimeAdapter(this, allData);
            sehriIftarTimeListView.setAdapter(sehriIftarTimeAdapter);
        }

        new AdmobAddManager().showInterStitialAd(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
        }
        realmManager.closeRealmServices();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}
