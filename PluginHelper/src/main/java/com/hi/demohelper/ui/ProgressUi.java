package com.hi.demohelper.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ProgressUi {
    public static ProgressDialog getProgressDialog(Context context) {

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("下载进度");
        dialog.setMax(100);
        dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.setProgress(0);
        return dialog;

    }
}
