package com.hi.demohelper.load;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

public class PluginHelper {

    public static final String PLUGIN_MAIL = "mail";
    public static Map<String, Resources> resMap = new HashMap<>();
    public static Map<String, String> dirMap = new HashMap<>();
    public static DexClassLoader dexClassLoader;


    public static boolean loadFile(String fileName, String downloadDir, Context context, String routeTablePkg) {
        String optDir = context.getDir("plugin", Context.MODE_PRIVATE).getAbsolutePath();
        String filePath = new File(downloadDir, fileName).getAbsolutePath();
        dirMap.put(PLUGIN_MAIL,filePath);
        dexClassLoader = new DexClassLoader(filePath, optDir, null, context.getClassLoader());

        boolean result = false;
        try {
            Class<?> libProviderCls = dexClassLoader.loadClass(routeTablePkg);
            libProviderCls.newInstance();

            AssetManager manager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(manager, filePath);
            Resources resources = new Resources(manager,
                    context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
            resMap.put(PLUGIN_MAIL, resources);

            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
