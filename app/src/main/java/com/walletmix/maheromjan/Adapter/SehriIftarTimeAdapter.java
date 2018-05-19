package com.walletmix.maheromjan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.walletmix.maheromjan.Database.SingleRow;
import com.walletmix.maheromjan.R;
import com.walletmix.maheromjan.Managers.SehriIftarPlusMinusManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by Shishir on 14/05/2018.
 */

public class SehriIftarTimeAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<SingleRow> dataList;
    private SehriIftarPlusMinusManager sehriIftarPlusMinusManager;



    public SehriIftarTimeAdapter(Context context, RealmResults<SingleRow> dataList) {
        this.context = context;
        this.dataList = dataList;
        sehriIftarPlusMinusManager = new SehriIftarPlusMinusManager(context);
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_row_for_sehri_iftar_time, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        SingleRow row = dataList.get(position);
        String sehriT = sehriIftarPlusMinusManager.getSehri(row.getSehriTime());
        String iftarT = sehriIftarPlusMinusManager.getIftar(row.getIftarTime());
        holder.dateTv.setText(getInBangla(row.getEnglishDate()));
        holder.dayTv.setText(row.getDay());
        holder.sehriTime.setText(getInBangla(sehriT));
        holder.iftarTime.setText(getInBangla(iftarT));
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.dateTv)
        TextView dateTv;

        @BindView(R.id.dayTv)
        TextView dayTv;

        @BindView(R.id.sehriTimeTv)
        TextView sehriTime;

        @BindView(R.id.iftarTimeTv)
        TextView iftarTime;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



    public String getInBangla(String string) {
        Character bangla_number[] = {'১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯', '০'};
        Character eng_number[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        String values = "";
        char[] character = string.toCharArray();
        for (int i = 0; i < character.length; i++) {
            Character c = ' ';
            for (int j = 0; j < eng_number.length; j++) {
                if (character[i] == eng_number[j]) {
                    c = bangla_number[j];
                    break;
                } else {
                    c = character[i];
                }
            }
            values = values + c;
        }
        return values;
    }
}
