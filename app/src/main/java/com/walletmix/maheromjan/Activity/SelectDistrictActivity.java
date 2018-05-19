package com.walletmix.maheromjan.Activity;

import android.content.Intent;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.walletmix.maheromjan.Database.SessionManager;
import com.walletmix.maheromjan.MainActivity;
import com.walletmix.maheromjan.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SelectDistrictActivity extends AppCompatActivity {


    @BindView(R.id.districtList)
    ListView districtListView;
    @BindView(R.id.searchDistrict)
    android.support.v7.widget.SearchView searchView;

    private Unbinder unbinder = null;
    private ActionBar actionBar;
    private ArrayAdapter<String> adapter;
    ArrayList<String> districts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SessionManager.getInstance(this).getString(SessionManager.Key.SELECTED_DISTRICT,"ss")=="ss"){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_select_district);
        unbinder = ButterKnife.bind(this);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        districts.addAll(Arrays.asList(getResources().getStringArray(R.array.districtArrayBangla)));
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,districts);
        districtListView.setAdapter(adapter);

        districtListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dis = (String) parent.getItemAtPosition(position);

                if (SessionManager.getInstance(SelectDistrictActivity.this).getString(SessionManager.Key.SELECTED_DISTRICT,"ss")=="ss"){
                    SessionManager.getInstance(SelectDistrictActivity.this).put(SessionManager.Key.SELECTED_DISTRICT,dis);
                    Intent intent = new Intent(SelectDistrictActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("dis",dis);
                    setResult(13, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()){
                    adapter.getFilter().filter(newText);
                }else{
                    districtListView.setAdapter(null);
                    adapter = new ArrayAdapter<String>(SelectDistrictActivity.this,android.R.layout.simple_list_item_1,districts);
                    districtListView.setAdapter(adapter);
                }
                return false;
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null){
            unbinder.unbind();
        }
    }
}
