package com.hi.demohelper.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    public static List<String> getFileList(String dir) {
        List<String> fileList = new ArrayList<>();
        File fileDir = new File(dir);
        if (fileDir.listFiles() == null || fileDir.listFiles().length == 0) {
            return fileList;
        }
        for (File file : fileDir.listFiles()) {
            fileList.add(file.getName());
        }
        return fileList;
    }
}
