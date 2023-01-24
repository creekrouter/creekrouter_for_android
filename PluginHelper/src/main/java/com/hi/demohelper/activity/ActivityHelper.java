package com.hi.demohelper.activity;

import android.content.Context;
import android.content.Intent;

import com.creek.router.annotation.CreekMethod;

public class ActivityHelper {
    @CreekMethod(path = "plugin_helper_startActivity")
    public void startActivity(Context context, String annotateBeanPath) {

        Intent intent = new Intent(context,PluginActivity.class);
        intent.putExtra("class_annotate",annotateBeanPath);
        context.startActivity(intent);
    }
}
