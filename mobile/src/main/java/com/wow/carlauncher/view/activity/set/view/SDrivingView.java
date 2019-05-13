package com.wow.carlauncher.view.activity.set.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;

import com.wow.carlauncher.R;
import com.wow.carlauncher.common.CommonData;
import com.wow.carlauncher.common.util.SharedPreUtil;
import com.wow.carlauncher.common.view.SetView;
import com.wow.carlauncher.ex.manage.toast.ToastManage;
import com.wow.carlauncher.view.activity.driving.AutoDrivingEnum;
import com.wow.carlauncher.view.activity.driving.DrivingViewEnum;
import com.wow.carlauncher.view.activity.set.SetActivity;
import com.wow.carlauncher.view.activity.set.SetBaseView;
import com.wow.carlauncher.view.activity.set.event.SAEventRefreshDriving;
import com.wow.carlauncher.view.activity.set.listener.SetEnumOnClickListener;
import com.wow.carlauncher.view.activity.set.listener.SetSwitchOnClickListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

import static com.wow.carlauncher.common.CommonData.AUTO_DRIVING_TYPES;
import static com.wow.carlauncher.common.CommonData.SDATA_AUTO_TO_DRIVING_TIME;
import static com.wow.carlauncher.common.CommonData.SDATA_AUTO_TO_DRIVING_TYPE;
import static com.wow.carlauncher.common.CommonData.SDATA_DRIVING_VIEW;

/**
 * Created by 10124 on 2018/4/22.
 */
@SuppressLint("ViewConstructor")
public class SDrivingView extends SetBaseView {
    public SDrivingView(SetActivity activity) {
        super(activity);
    }

    @Override
    protected int getContent() {
        return R.layout.content_set_driving;
    }

    @BindView(R.id.sv_driving_type)
    SetView sv_driving_type;

    @BindView(R.id.sv_auto_to_driving)
    SetView sv_auto_to_driving;

    @BindView(R.id.sv_auto_to_driving_time)
    SetView sv_auto_to_driving_time;

    @BindView(R.id.sv_auto_to_driving_type)
    SetView sv_auto_to_driving_type;

    @Override
    protected void initView() {
        sv_driving_type.setSummary(DrivingViewEnum.getById(SharedPreUtil.getInteger(SDATA_DRIVING_VIEW, DrivingViewEnum.BLACK.getId())).getName());
        sv_driving_type.setOnClickListener(new SetEnumOnClickListener<DrivingViewEnum>(getContext(), CommonData.DRIVING_VIEW) {
            @Override
            public String title() {
                return "请选择首页切换动画";
            }

            @Override
            public DrivingViewEnum getCurr() {
                return DrivingViewEnum.getById(SharedPreUtil.getInteger(SDATA_DRIVING_VIEW, DrivingViewEnum.BLACK.getId()));
            }

            @Override
            public void onSelect(DrivingViewEnum setEnum) {
                SharedPreUtil.saveInteger(SDATA_DRIVING_VIEW, setEnum.getId());
                sv_driving_type.setSummary(setEnum.getName());
                EventBus.getDefault().post(new SAEventRefreshDriving());
            }
        });

        sv_auto_to_driving.setOnValueChangeListener(new SetSwitchOnClickListener(CommonData.SDATA_AUTO_TO_DRIVING));
        sv_auto_to_driving.setChecked(SharedPreUtil.getBoolean(CommonData.SDATA_AUTO_TO_DRIVING, false));

        sv_auto_to_driving_type.setSummary(AutoDrivingEnum.getById(SharedPreUtil.getInteger(SDATA_AUTO_TO_DRIVING_TYPE, AutoDrivingEnum.TIME.getId())).getName());
        sv_auto_to_driving_type.setOnClickListener(new SetEnumOnClickListener<AutoDrivingEnum>(getContext(), AUTO_DRIVING_TYPES) {
            @Override
            public String title() {
                return "选择自动跳转方式";
            }

            @Override
            public AutoDrivingEnum getCurr() {
                return AutoDrivingEnum.getById(SharedPreUtil.getInteger(SDATA_AUTO_TO_DRIVING_TYPE, AutoDrivingEnum.TIME.getId()));
            }

            @Override
            public void onSelect(AutoDrivingEnum setEnum) {
                SharedPreUtil.saveInteger(SDATA_AUTO_TO_DRIVING_TYPE, setEnum.getId());
                sv_auto_to_driving_type.setSummary(setEnum.getName());
            }
        });

        sv_auto_to_driving_time.setSummary(SharedPreUtil.getInteger(SDATA_AUTO_TO_DRIVING_TIME, 60) + "");
        sv_auto_to_driving_time.setOnClickListener(v -> {
            EditText editText = new EditText(getContext());
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            new AlertDialog.Builder(getContext()).setTitle("请输入一个时间(单位秒,且大于15)").setNegativeButton("取消", null)
                    .setPositiveButton("确定", (dialog, which) -> {
                        try {
                            int time = Integer.parseInt(editText.getText().toString());
                            if (time < 15) {
                                ToastManage.self().show("时间必须大于15秒");
                                return;
                            }
                            SharedPreUtil.saveInteger(SDATA_AUTO_TO_DRIVING_TIME, time);
                            sv_auto_to_driving_time.setSummary(time + "");
                        } catch (Exception e) {
                            SharedPreUtil.saveInteger(SDATA_AUTO_TO_DRIVING_TIME, 60);
                        }
                        dialog.dismiss();
                    }).setView(editText).show();
        });
    }
}
