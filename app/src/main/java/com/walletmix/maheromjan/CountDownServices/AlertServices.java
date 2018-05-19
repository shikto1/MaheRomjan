package com.walletmix.maheromjan.CountDownServices;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Siddhartha on 13/05/2018.
 */

public class AlertServices {

    public static void shoToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
