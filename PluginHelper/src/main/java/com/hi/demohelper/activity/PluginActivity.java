package com.hi.demohelper.activity;


import static com.hi.demohelper.load.PluginHelper.PLUGIN_MAIL;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.creek.router.CreekRouter;
import com.creek.router.annotation.CreekClass;
import com.hi.demohelper.R;
import com.hi.demohelper.load.PluginHelper;
import com.hi.demohelper.load.PluginPkgManager;

import dalvik.system.DexClassLoader;

@CreekClass(path = "plugin_helper_PluginActivity")
public class PluginActivity extends FragmentActivity {

    public PluginProxy proxy;
    public PluginPkgManager pluginPkgManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String class_annotate = getIntent().getStringExtra("class_annotate");
        Object instance = CreekRouter.getBean(class_annotate);
        proxy = CreekRouter.create(PluginProxy.class, instance);
        if (proxy == null || instance == null) {
            setContentView(R.layout.activity_plugin);
        } else {
            proxy.attach(this);
            proxy.resources(PluginHelper.resMap.get(PLUGIN_MAIL));
            proxy.onCreate(savedInstanceState);
        }

    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginHelper.dexClassLoader;
    }

//    @Override
//    public Resources getResources() {
//        return PluginHelper.resMap.get(PLUGIN_MAIL);
//    }


//    @Override
//    public PackageManager getPackageManager() {
//        if (pluginPkgManager == null) {
//            pluginPkgManager = new PluginPkgManager(super.getPackageManager());
//        }
//        return pluginPkgManager;
//    }
}