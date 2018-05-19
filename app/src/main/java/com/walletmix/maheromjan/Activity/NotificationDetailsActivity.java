package com.walletmix.maheromjan.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.walletmix.maheromjan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class NotificationDetailsActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.detailsTv)
    TextView detailsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        unbinder = ButterKnife.bind(this);

        if (getIntent() != null){
            String title = getIntent().getStringExtra("title");
            String msg = getIntent().getStringExtra("msg");
            titleTv.setText(title);
            detailsTv.setText(msg);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_two, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share_menu) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, detailsTv.getText().toString());
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
