package com.walletmix.maheromjan.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.walletmix.maheromjan.Database.SessionManager;
import com.walletmix.maheromjan.MainActivity;
import com.walletmix.maheromjan.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WelComeToMaheRamjanActivity extends AppCompatActivity {


    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String dis = SessionManager.getInstance(this).getString(SessionManager.Key.SELECTED_DISTRICT,"ss");
        if (dis != "ss"){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_wel_come_to_mahe_ramjan);
        unbinder = ButterKnife.bind(this);
    }


    @OnClick(R.id.jelaNirbachonButton)
    void onCLick(Button button){
        Intent intent = new Intent(this, SelectDistrictActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder !=null){
            unbinder.unbind();
        }
    }
}
