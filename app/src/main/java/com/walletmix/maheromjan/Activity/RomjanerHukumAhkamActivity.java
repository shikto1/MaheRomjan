package com.walletmix.maheromjan.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.walletmix.maheromjan.AllKey;
import com.walletmix.maheromjan.Managers.AdmobAddManager;
import com.walletmix.maheromjan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RomjanerHukumAhkamActivity extends AppCompatActivity {


    private Unbinder unbinder;
    private ActionBar actionBar;


    @BindView(R.id.textHere)
    TextView detailsText;

    @BindView(R.id.scollView)
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_romjaner_hukum_ahkam);
        unbinder = ButterKnife.bind(this);
        actionBar = getSupportActionBar();

        new AdmobAddManager().showInterStitialAd(this);

        if (getIntent() != null) {
            String menu = getIntent().getStringExtra("menu");
            switch (menu) {
                case AllKey.RUJA_VONGER_KARON: {
                    detailsText.setText(getString(R.string.rujaVongerKaron));
                    actionBar.setTitle(getString(R.string.rujaVongerKaronTitle));
                    formatTitle();
                    break;
                }
                case AllKey.RUJA_VONGER_KARON_NOI: {
                    detailsText.setText(getString(R.string.rujaVongerKaronNoi));
                    actionBar.setTitle(getString(R.string.rujaVongerKaronNoiTitleTwo));
                    formatTitle();
                    break;
                }
                case AllKey.MAHE_RAMJANER_FAJILOT:{
                    detailsText.setText(getString(R.string.rojmajerHukumAhkam));
                    actionBar.setTitle(getString(R.string.maheRomjanerFojilot));
                    formatTitle();
                }
            }
        }
    }


    public void formatTitle() {
        TextView tv = new TextView(getApplicationContext());
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, // Width of TextView
                ActionBar.LayoutParams.WRAP_CONTENT); // Height of TextView
        tv.setLayoutParams(lp);
        tv.setText(actionBar.getTitle()); // ActionBar title text
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
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
