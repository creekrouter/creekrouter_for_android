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

package com.creek.router.configure;


import com.creek.router.generate.txt.TxtCreator;

public class TxtPath {


    public static String PATH_SCAN_RES;

    public static String Log_Dir;
    public static String LOG_INFO;


    public static void init() {
        String DIR_SEPARATOR = Profile.getInstance().getDirectorySeparator();
        String base_dir = CreekXml.logDir + DIR_SEPARATOR + CreekXml.SDKName + DIR_SEPARATOR + "process" + DIR_SEPARATOR;
        PATH_SCAN_RES = base_dir + Profile.getInstance().getModuleName() + "_" + "scan.txt";
        LOG_INFO = CreekXml.logDir + DIR_SEPARATOR + CreekXml.SDKName + DIR_SEPARATOR + "log.txt";
        Log_Dir = base_dir;

         /*
          以此创建1个目录、2个文件；
        */
        TxtCreator.createDir(Log_Dir);
        TxtCreator.createFileIfNone(PATH_SCAN_RES);
        TxtCreator.createFileIfNone(LOG_INFO);
    }


}
