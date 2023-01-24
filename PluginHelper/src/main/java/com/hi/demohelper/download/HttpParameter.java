package com.hi.demohelper.download;


import android.content.Context;

public class HttpParameter {

    public String url;
    public String downLoadDir;
    public Context context;


    public HttpParameter(String url, String downLoadDir,Context context) {
        this.url = url;
        this.downLoadDir = downLoadDir;
        this.context = context;
    }

    public String getDownLoadFileName() {
        return url.substring(url.lastIndexOf("/") + 1);
    }


}
