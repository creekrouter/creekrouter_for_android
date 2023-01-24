/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * CreekRouter
 * Author:LvXiaofei
 * Email:creekrouter@163.com
 */

package com.creek.router.generate.txt;


import com.creek.router.bean.RouterBean;
import com.creek.router.configure.TxtPath;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Set;

public class TxtWriter {

    public static void txtAppendWrite(String txtPath, String content) {
        TxtCreator.createFileIfNone(txtPath);
        String txtContent = TxtReader.readTxt(txtPath).trim();
        writeTxt(txtPath, txtContent + "\n" + content);
    }

    public static void writeTxt(String txtPath, String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(txtPath));
            out.write(info); // \r\n为换行
//            out.flush(); // 把缓存区内容压入文件
            out.close();
        } catch (Exception e) {
        }
    }

    public static void writeScanAnnotation(Set<RouterBean> clsSet, Set<RouterBean> bSet, Set<RouterBean> askSet, Set<RouterBean> impSet) {
        StringBuilder sb = new StringBuilder();
        for (RouterBean routerBean : clsSet) {
            sb.append(routerBean.toString());
            sb.append("\n");
        }
        if (clsSet.size() > 0) {
            sb.append("\n");
        }
        for (RouterBean routerBean : bSet) {
            sb.append(routerBean.toString());
            sb.append("\n");
        }
        if (bSet.size() > 0) {
            sb.append("\n");
        }
        for (RouterBean routerBean : askSet) {
            sb.append(routerBean.toString());
            sb.append("\n");
        }
        if (askSet.size() > 0) {
            sb.append("\n");
        }
        for (RouterBean routerBean : impSet) {
            sb.append(routerBean.toString());
            sb.append("\n");
        }
        writeTxt(TxtPath.PATH_SCAN_RES, sb.toString());
    }

}
