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

package com.creek.router.processor;

import com.creek.router.annotation.CreekFlag;
import com.creek.router.configure.Config;
import com.creek.router.configure.CreekXml;
import com.creek.router.configure.Profile;
import com.creek.router.generate.java.GenPluginInitializer;
import com.creek.router.generate.java.GenRoutingTable;
import com.creek.router.generate.java.GenInitializer;
import com.creek.router.generate.txt.TxtCreator;
import com.creek.router.generate.txt.TxtLogger;
import com.creek.router.generate.txt.TxtReader;
import com.creek.router.generate.txt.TxtWriter;
import com.creek.router.processor.base.BaseProcessor;
import com.google.auto.service.AutoService;


import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;

@AutoService(Processor.class)
public class LastProcessor extends BaseProcessor {

    private String txt_creek_list, txt_last_add, txt_last_add_bp;
    private String lastAdder, lastAdder_bp;

    private String LOG_DIR, DIR_SEPARATOR;

    @Override
    public void init() {
        LOG_DIR = CreekXml.logDir;
        DIR_SEPARATOR = Profile.getInstance().getDirectorySeparator();
        txt_creek_list = LOG_DIR + DIR_SEPARATOR + CreekXml.SDKName + DIR_SEPARATOR + "creekImpl.txt";
        txt_last_add = LOG_DIR + DIR_SEPARATOR + CreekXml.SDKName + DIR_SEPARATOR + "lastImpl.txt";
        txt_last_add_bp = LOG_DIR + DIR_SEPARATOR + CreekXml.SDKName + DIR_SEPARATOR + "lastImpl_bp.txt";

        TxtCreator.createFileIfNone(txt_creek_list);
        TxtCreator.createFileIfNone(txt_last_add);
        TxtCreator.createFileIfNone(txt_last_add_bp);
    }

    @Override
    public Set<String> getScanAnnotation() {
        Set<String> annotationSet = new HashSet<>();
        annotationSet.add(CreekFlag.class.getName());
        TxtLogger.flushLog();
        return annotationSet;
    }

    @Override
    public void process(RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            if (GenRoutingTable.execute) {
                TxtWriter.txtAppendWrite(txt_creek_list, Config.PACKAGE + "." + GenRoutingTable.CLASS_NAME);
            }
//            if (CreekXml.appModule == null || CreekXml.appModule.length() == 0) {
//                lastAdder = TxtReader.readTxt(txt_last_add).trim();
//                lastAdder_bp = TxtReader.readTxt(txt_last_add_bp).trim();
//
//                File file = new File(lastAdder);
//                File file_bp = new File(lastAdder_bp);
//
//                if (file.exists()) {
//                    file.delete();
//                }
//                if (file_bp.exists()) {
//                    file_bp.delete();
//                }
//
//                Set<String> set = TxtReader.readTrimLine(txt_creek_list);
//                GenMainRoutingTable.gen(set, filerGen);
//                GenMainRoutingTable.genBackUp(set, filerGen);
//                TxtWriter.writeTxt(txt_last_add, GenMainRoutingTable.FILE_PATH);
//                TxtWriter.writeTxt(txt_last_add_bp, GenMainRoutingTable.FILE_PATH_BACK_UP);
//            }

            if (CreekXml.appModule.equals(Profile.getInstance().getModuleName())) {
                Set<String> set = TxtReader.readTrimLine(txt_creek_list);
                GenInitializer.gen(set, filerGen);
                GenPluginInitializer.gen(set,filerGen);
                TxtLogger.flushLog();
                print("======================== scan over ***********************");
            }
        }
    }
}
