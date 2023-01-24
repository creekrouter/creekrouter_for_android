package com.hi.demohelper.file;


import com.hi.demohelper.download.StatusListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WriteFile {


    //将输入流写入文件
    public static void writeFileFromIS(File file, InputStream is, long totalLength, final StatusListener listener) {

        int sBufferSize = 8192;

        //创建文件
//        if (!file.exists()) {
//            if (!file.getParentFile().exists())
//                file.getParentFile().mkdir();
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//                downloadListener.onFail("createNewFile IOException",Constant.ERROR_CREATE_FILE);
//            }
//        }

        OutputStream os = null;
        long currentLength = 0;
        boolean isCloseFlag = false;
        boolean osCloseFlag = false;
        boolean writeComplete = false;

        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
                currentLength += len;
                //计算当前下载进度
                int progress = (int) (100 * currentLength / totalLength);
                listener.onProgress(progress, currentLength, totalLength);
            }
            writeComplete = true;
        } catch (IOException e) {
            writeComplete = false;
            listener.onFail(e.getMessage());
        } finally {
            try {
                is.close();
                isCloseFlag = true;
            } catch (IOException e) {
                isCloseFlag = false;
            }
            try {
                if (os != null) {
                    os.close();
                }
                osCloseFlag = true;
            } catch (IOException e) {
                osCloseFlag = false;
            }
        }
        if (writeComplete && isCloseFlag && osCloseFlag){
            //下载完成，并返回保存的文件路径
            listener.onFinish(file.getAbsolutePath());
        }else {
            listener.onFail("下载失败！");
        }
    }


}
