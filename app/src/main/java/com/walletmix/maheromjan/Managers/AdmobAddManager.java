package com.walletmix.maheromjan.Managers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.walletmix.maheromjan.R;

/**
 * Created by Siddhartha on 17/05/2018.
 */

public class AdmobAddManager {

    private AdView mAdView;
    private InterstitialAd mInterStitialAd;

    //Show Interstitial Ad......................

    public void showInterStitialAd(Context context) {
        mInterStitialAd = new InterstitialAd(context);
        mInterStitialAd.setAdUnitId(context.getString(R.string.interstitial_ad_id));
        mInterStitialAd.loadAd(new AdRequest.Builder().build());
        mInterStitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterStitialAd.show();
            }
        });
    }
    public void showInterStitialAdTwo(Context context) {
        mInterStitialAd = new InterstitialAd(context);
        mInterStitialAd.setAdUnitId(context.getString(R.string.interstitial_ad_id_two));
        mInterStitialAd.loadAd(new AdRequest.Builder().build());
        mInterStitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterStitialAd.show();
            }
        });
    }


    //Show Banner Ad.................................

    public void showBannerToActivity(Context context, int viewId) {
        mAdView = (AdView) ((AppCompatActivity) context).findViewById(viewId);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
    public void showBannerAddToFragment(View view, int viewId) {
        mAdView = (AdView) view.findViewById(viewId);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
