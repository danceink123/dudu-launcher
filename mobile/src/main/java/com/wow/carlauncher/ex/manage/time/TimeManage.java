package com.wow.carlauncher.ex.manage.time;

import android.annotation.SuppressLint;
import android.content.Context;

import com.wow.carlauncher.common.LogEx;
import com.wow.carlauncher.common.TaskExecutor;
import com.wow.carlauncher.ex.ContextEx;
import com.wow.carlauncher.ex.manage.time.event.MTime30MinuteEvent;
import com.wow.carlauncher.ex.manage.time.event.MTime3SecondEvent;
import com.wow.carlauncher.ex.manage.time.event.MTimeHalfSecondEvent;
import com.wow.carlauncher.ex.manage.time.event.MTimeMinuteEvent;
import com.wow.carlauncher.ex.manage.time.event.MTimeSecondEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ScheduledFuture;

/**
 * Created by 10124 on 2018/4/25.
 */

public class TimeManage extends ContextEx {
    private static class SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        private static TimeManage instance = new TimeManage();
    }

    public static TimeManage self() {
        return TimeManage.SingletonHolder.instance;
    }

    private TimeManage() {
        super();
    }

    public void init(Context context) {
        setContext(context);
        startTimer();
        LogEx.d(this, "init");
    }

    private final static int ZHOUQI = 500;

    private final static int MSECOND = 1000;
    private final static int SECOND = MSECOND / ZHOUQI;

    private final static int MSECOND3 = 3000;
    private final static int SECOND3 = MSECOND3 / ZHOUQI;

    private final static int MMINUTE = 60 * 1000;
    private final static int MINUTE = MMINUTE / ZHOUQI;

    private final static int MMINUTE30 = 30 * 60 * 1000;
    private final static int MINUTE30 = MMINUTE30 / ZHOUQI;

    private ScheduledFuture<?> timer;
    private long timeMark = 0L;

    private void startTimer() {
        stopTimer();
        LogEx.d(this, "startTimer");
        timer = TaskExecutor.self().repeatRun(() -> {
            try {
                if (EventBus.getDefault().hasSubscriberForEvent(MTimeHalfSecondEvent.class)) {
                    postEvent(new MTimeHalfSecondEvent());
                    LogEx.d(this, "timerun:MTimeHalfSecondEvent");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (timeMark % SECOND == 0) {
                    if (EventBus.getDefault().hasSubscriberForEvent(MTimeSecondEvent.class)) {
                        postEvent(new MTimeSecondEvent());
                        LogEx.d(this, "timerun:MTimeSecondEvent");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (timeMark % SECOND3 == 0) {
                    if (EventBus.getDefault().hasSubscriberForEvent(MTime3SecondEvent.class)) {
                        postEvent(new MTime3SecondEvent());
                        LogEx.d(this, "timerun:MTime3SecondEvent");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (timeMark % MINUTE30 == 0) {
                    if (EventBus.getDefault().hasSubscriberForEvent(MTime30MinuteEvent.class)) {
                        postEvent(new MTime30MinuteEvent());
                        LogEx.d(this, "timerun:MTime30MinuteEvent");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (timeMark % MINUTE == 0) {
                    if (EventBus.getDefault().hasSubscriberForEvent(MTimeMinuteEvent.class)) {
                        postEvent(new MTimeMinuteEvent());
                        LogEx.d(this, "timerun:MTimeMinuteEvent");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            timeMark++;
        }, ZHOUQI, ZHOUQI);
    }

    private void stopTimer() {
        LogEx.d(this, "stopTimer");
        if (timer != null) {
            timer.cancel(true);
            timer = null;
        }
    }
}
