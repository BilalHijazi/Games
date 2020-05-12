package com.example.games;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

            Intent serviceIntent = new Intent(context, NewsService.class);
            context.startService(serviceIntent);




    }
}