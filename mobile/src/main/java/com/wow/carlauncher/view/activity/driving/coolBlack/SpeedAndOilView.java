package com.wow.carlauncher.view.activity.driving.coolBlack;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.wow.carlauncher.R;
import com.wow.carlauncher.common.TaskExecutor;
import com.wow.carlauncher.ex.plugin.obd.ObdPlugin;
import com.wow.carlauncher.ex.plugin.obd.evnet.PObdEventCarInfo;
import com.wow.carlauncher.view.base.BaseView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by 10124 on 2018/4/26.
 */

public class SpeedAndOilView extends BaseView {
    private final static int RATE = 100;
    private final static int MAX_SPEED = 200 * RATE;

    private final static int MAX_OIL = 100;
    @BindView(R.id.iv_cursor)
    ImageView iv_cursor;

    @BindView(R.id.iv_oil)
    ImageView iv_oil;

    @BindView(R.id.tv_speed)
    TextView tv_speed;

    @BindView(R.id.tv_oil)
    TextView tv_oil;

    private boolean show = false;

    private int currentValue = 0;
    private int tagerValue = 0;
    private int revChangeValue = 1;//转速变化的区间

    public SpeedAndOilView(Context context) {
        super(context);
    }

    public SpeedAndOilView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected int getContent() {
        return R.layout.content_driving_cool_speed_and_oil;
    }

    @Override
    protected void initView() {
        //同步一下信息
        TaskExecutor.self().post(() -> {
            onEventMainThread(ObdPlugin.self().getCurrentPObdEventCarInfo());
        }, 500);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        iv_cursor.setPivotX(getMeasuredWidth() / 2);
        iv_cursor.setPivotY(getMeasuredHeight() / 2);//支点在图片中心

        iv_oil.setPivotX(getMeasuredWidth() / 2);
        iv_oil.setPivotY(getMeasuredHeight() / 2);//支点在图片中心
    }

    public void setOil(int oil) {
        if (oil > MAX_OIL) {
            oil = MAX_OIL;
        } else if (oil < 0) {
            oil = 0;
        }
        iv_oil.setRotation(-(float) (oil * 90) / (float) MAX_OIL);

        tv_oil.setText("剩余油量:" + oil + "%");
    }

    public void setSpeed(int speed) {
        tv_speed.setText(speed + "");

        speed = speed * RATE;
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        } else if (speed < 0) {
            speed = 0;
        }
        tagerValue = speed;
        revChangeValue = Math.abs(tagerValue - currentValue) / 100;
        if (revChangeValue < 1) {
            revChangeValue = 1;
        }
        postValue();
    }

    private void postValue() {
        if (revChangeValue + currentValue < tagerValue) {
            currentValue = currentValue + revChangeValue;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    iv_cursor.setRotation((float) (currentValue * 270) / (float) MAX_SPEED);
                    postValue();
                }
            }, 1);
        } else if (revChangeValue + currentValue > tagerValue) {
            currentValue = currentValue - revChangeValue;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    iv_cursor.setRotation((float) (currentValue * 270) / (float) MAX_SPEED);
                    postValue();
                }
            }, 1);
        }
    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventMainThread(final PObdEventCarInfo event) {
        post(new Runnable() {
            @Override
            public void run() {
                if (event.getSpeed() != null) {
                    setSpeed(event.getSpeed());
                }
                if (event.getOilConsumption() != null) {
                    setOil(event.getOilConsumption());
                }
            }
        });
    }
}
