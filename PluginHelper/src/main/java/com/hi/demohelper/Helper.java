package com.hi.demohelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.creek.router.annotation.CreekMethod;
import com.hi.demohelper.download.HttpParameter;
import com.hi.demohelper.download.OkHttpDownload;
import com.hi.demohelper.download.StatusListener;
import com.hi.demohelper.file.FileHelper;
import com.hi.demohelper.load.PluginHelper;
import com.hi.demohelper.ui.OnItemClickListener;
import com.hi.demohelper.ui.PopWindow;
import com.hi.demohelper.ui.ProgressUi;

import java.io.File;
import java.util.List;

public class Helper {

    @CreekMethod(path = "demo_help_download")
    public static void download(Context context, String url) {
        String downloadDir = context.getDir("download", Context.MODE_PRIVATE).getAbsolutePath();
        HttpParameter parameter = new HttpParameter(url, downloadDir, context);
        ProgressDialog dialog = ProgressUi.getProgressDialog(context);
        Activity activity = (Activity) context;
        OkHttpDownload.downLoadFile(parameter, new StatusListener() {
            @Override
            public void onStart() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
                    }
                });
            }

            @Override
            public void onProgress(int progress, long currentLength, long totalLength) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setProgress(progress);
                    }
                });
            }

            @Override
            public void onFinish(String info) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(context, "download success !", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onFail(String errorInfo) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(context, "download failed !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @CreekMethod(path = "demo_help_load")
    public static void loadPlugin(Context context, String routeTablePkg) {
        String downloadDir = context.getDir("download", Context.MODE_PRIVATE).getAbsolutePath();
        List<String> childFiles = FileHelper.getFileList(downloadDir);
        if (childFiles.size() == 0) {
            Toast.makeText(context, "There has no Plugin File can load!\n Please download first!", Toast.LENGTH_SHORT).show();
            return;
        }
        PopWindow.popListWindow(context, childFiles, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (PluginHelper.loadFile(childFiles.get(position), downloadDir, context, routeTablePkg)) {
                    Toast.makeText(context, "pluginApp load finish", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "pluginApp load error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @CreekMethod(path = "demo_help_clear")
    public static void clearPlugin(Context context) {
        String downloadDir = context.getDir("download", Context.MODE_PRIVATE).getAbsolutePath();
        File dir = new File(downloadDir);
        for (File file : dir.listFiles()) {
            file.delete();
        }
        Toast.makeText(context, "clear finished!", Toast.LENGTH_SHORT).show();
    }
}
