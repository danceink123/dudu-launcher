package com.wow.carlauncher.dialog;

import android.content.Context;

import com.wow.carlauncher.R;
import com.wow.carlauncher.common.BaseDialog;

/**
 * Created by 10124 on 2017/11/5.
 */

public class InputDialog extends BaseDialog {
    public InputDialog(Context context) {
        super(context);
        setGravityCenter();
        setContent(R.layout.dialog_input);
    }
}
