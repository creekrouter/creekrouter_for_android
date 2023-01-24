package com.hi.demohelper.download;

public interface StatusListener {
    void onStart();
    void onProgress(int progress, long currentLength, long totalLength);
    void onFinish(String info);
    void onFail(String errorInfo);
}
