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
 * Author:lvxiaofei
 * Email:lvxiaofei6@gmail.com
 */

package com.creek.router.generate.txt;

import com.creek.router.bean.RouterBean;
import com.creek.router.configure.TxtPath;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class TxtLogger {
    private static List<String> delayWrite = new ArrayList<>();
    private static String HEAD_SPACE = "  ";

    public static void logTxt(String moduleName, Set<RouterBean> rClassSet, Set<RouterBean> rRouterBeanSet, Set<RouterBean> rMethodAskSet, Set<RouterBean> rMethodImplSet) {

        StringBuilder sb = new StringBuilder();
        String space = "  ";

        StringBuilder methodBody = new StringBuilder();
        StringBuilder clazzBody = new StringBuilder();
        StringBuilder beanBody = new StringBuilder();


        sb.append("【" + moduleName + "】\n");
        sb.append("注解 RouterClass 有：" + rClassSet.size() + " 个。\n");
        int clazzNum = 1;
        for (RouterBean routerBean : rClassSet) {
            clazzBody.append(space);
            clazzBody.append(clazzNum + "、注解值：");
            clazzBody.append(routerBean.path);
            clazzBody.append(getSpaceString(25 - routerBean.path.length()));
            clazzBody.append("包名：");
            clazzBody.append(routerBean.pkgName);
            clazzBody.append("\n");
            clazzNum++;
        }
        sb.append(clazzBody);

        sb.append("注解 RouterBean ：" + rRouterBeanSet.size() + " 个。\n");
        int beanNum = 1;
        for (RouterBean routerBean : rClassSet) {
            beanBody.append(space);
            beanBody.append(beanNum + "、注解值：");
            beanBody.append(routerBean.path);
            beanBody.append(getSpaceString(25 - routerBean.path.length()));
            beanBody.append("包名：");
            beanBody.append(routerBean.pkgName);
            beanBody.append("\n");
            beanNum++;
        }
        sb.append(beanBody);

        sb.append("注解 RouterMethod ：" + (rMethodImplSet.size() + rMethodAskSet.size()) + " 个。\n");

        int methodNum = 1;
        for (RouterBean routerBean : rMethodAskSet) {
            methodBody.append(space);
            methodBody.append(methodNum + "、Ask  注解值：");
            methodBody.append(routerBean.path);
            methodBody.append(getSpaceString(25 - routerBean.path.length()));
            methodBody.append("方法名：");
            methodBody.append(routerBean.method);
            methodBody.append(getSpaceString(20 - routerBean.method.length()));
            methodBody.append("包名：");
            methodBody.append(routerBean.pkgName);
            methodBody.append("\n");
            methodNum++;
        }
        sb.append("\n");
        methodNum = 1;
        for (RouterBean routerBean : rMethodAskSet) {
            methodBody.append(space);
            methodBody.append(methodNum + "、Reply注解值：");
            methodBody.append(routerBean.path);
            methodBody.append(getSpaceString(25 - routerBean.path.length()));
            methodBody.append("方法名：");
            methodBody.append(routerBean.method);
            methodBody.append(getSpaceString(20 - routerBean.method.length()));
            methodBody.append("包名：");
            methodBody.append(routerBean.pkgName);
            methodBody.append("\n");
            methodNum++;
        }
        sb.append(methodBody);

        TxtLogger.output_in_section(sb.toString());
    }

    public static String getSpaceString(int num) {
        if (num <= 0) {
            return "";
        }
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < num; i++) {
            space.append(" ");
        }
        return space.toString();
    }


    public static void output_new_section(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n");
        sb.append("Router-Log-Time:");
        sb.append(getTime());
        sb.append("\n");
        sb.append("-------------------------------------\n" + HEAD_SPACE);
        sb.append(content.replaceAll("\n", "\n" + HEAD_SPACE));
        delayWrite.add(sb.toString());
        delayWriteLog();
    }

    public static void output_in_section(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(HEAD_SPACE + "time:");
        sb.append(getTime());
        sb.append("\n" + HEAD_SPACE);
        sb.append(content.replaceAll("\n", "\n" + HEAD_SPACE));
        sb.append("\n-----------------------------------------------------------------------------");
        sb.append("-----------------------------------------------------------------------------\n");
        delayWrite.add(sb.toString());
        delayWriteLog();
    }

    public static boolean flushLog() {
        return delayWriteLog();
    }

    private static boolean delayWriteLog() {
        if (TxtPath.LOG_INFO == null || TxtPath.LOG_INFO.length() == 0) {
            return false;
        }
        if (delayWrite.size() == 0) {
            return true;
        }
        TxtCreator.createDir(TxtPath.Log_Dir);
        TxtCreator.createFileIfNone(TxtPath.LOG_INFO);
        StringBuilder allTxt = new StringBuilder();
        for (String str : delayWrite) {
            allTxt.append(str);
        }
        delayWrite.clear();
        write(allTxt.toString());
        return true;
    }

    private static void write(String txtContent) {
        FileWriter fw = null;
        try {
            // 传递true，代表不覆盖已有文件，追加
            fw = new FileWriter(TxtPath.LOG_INFO, true);
            fw.write(txtContent);
        } catch (IOException e) {

        } finally {
            try {
                if (fw != null)
                    fw.close();
            } catch (IOException e) {

            }
        }
    }

    private static String getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
