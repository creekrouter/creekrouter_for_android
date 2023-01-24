package com.hi.demohelper.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.creek.router.annotation.CreekMethod;

public interface PluginProxy {
    @CreekMethod(path = "plugin_mail_attache")
    void attach(Activity proxyActivity);

    @CreekMethod(path = "plugin_mail_resources")
    void resources(Resources res);


    @CreekMethod(path = "plugin_mail_setContentView_view")
    void setContentView(View view);

    @CreekMethod(path = "plugin_mail_setContentView_id")
    void setContentView(int layoutResID);

    @CreekMethod(path = "plugin_mail_findViewById")
    View findViewById(int id);

    /**
     * 生命周期
     */
    @CreekMethod(path = "plugin_mail_onCreate")
    void onCreate(Bundle saveInstanceState);

    @CreekMethod(path = "plugin_mail_onStart")
    void onStart();

    @CreekMethod(path = "plugin_mail_onResume")
    void onResume();

    @CreekMethod(path = "plugin_mail_onPause")
    void onPause();

    @CreekMethod(path = "plugin_mail_onStop")
    void onStop();

    @CreekMethod(path = "plugin_mail_onDestroy")
    void onDestroy();

    @CreekMethod(path = "plugin_mail_onSaveInstanceState")
    void onSaveInstanceState(Bundle outState);

    @CreekMethod(path = "plugin_mail_onTouchEvent")
    boolean onTouchEvent(MotionEvent event);

    @CreekMethod(path = "plugin_mail_onBackPressed")
    void onBackPressed();
}
