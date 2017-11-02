package com.wow.carlauncher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.wow.carlauncher.service.MainService;

import org.xutils.x;

/**
 * Created by 10124 on 2017/10/30.
 */

public class ScreenReceiver extends BroadcastReceiver {
    private static final String SCREEN_ON = "android.intent.action.SCREEN_ON";

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (SCREEN_ON.equals(intent.getAction())) {
            Intent startIntent = new Intent(context, MainService.class);
            context.startService(startIntent);
        }
    }
}
