package com.wow.carlauncher.plugin.console;

import android.content.Context;

/**
 * Created by 10124 on 2017/10/26.
 */

public abstract class ConsoleProtocl {
    protected Context context;
    protected ConsoleListener listener;

    public ConsoleProtocl(Context context, ConsoleListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public abstract void decVolume();

    public abstract void incVolume();

    public abstract void mute();

    public abstract void clearTask();

    public abstract void callAnswer();

    public abstract void callHangup();

    public abstract void destroy();
}
