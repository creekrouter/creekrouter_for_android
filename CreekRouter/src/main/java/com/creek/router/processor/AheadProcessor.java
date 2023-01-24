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

import com.creek.router.configure.Config;
import com.creek.router.configure.CreekXml;
import com.creek.router.configure.Profile;
import com.creek.router.configure.TxtPath;
import com.creek.router.processor.base.BaseProcessor;
import com.creek.router.tools.XmlParser;
import com.google.auto.service.AutoService;

import java.io.File;
import java.net.URI;

import javax.annotation.processing.Processor;


@AutoService(Processor.class)
public class AheadProcessor extends BaseProcessor {

    @Override
    public void init() {
        genFlagJavaClass();
        TxtPath.init();
    }

    private void genFlagJavaClass() {
        String tmpClsName = Config.TEMP_FLAG_CLASS_NAME + System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        sb.append("package " + Config.PACKAGE + ";\n");
        sb.append("import " + Config.PACKAGE_ANNOTATION + ".*;\n\n");
        sb.append("@" + Config.ANNOTATION_CREEK_FLAG + "\n");
        sb.append("public class " + tmpClsName + " { }\n");
        URI uri = this.filerGen.genJavaClass(sb.toString(), tmpClsName);
        File flagClassFile = new File(uri);
        initDirPathInfo(flagClassFile.getAbsolutePath(),tmpClsName);
    }


    private void initDirPathInfo(String genJavaPath,String tempClassName) {

        String flagFileName = tempClassName + ".java";
        String strA = genJavaPath.replace(flagFileName, "");
        int genIndex = strA.lastIndexOf(Config.PACKAGE_SUFFIX);
        String DIRECTORY_SEPARATOR = strA.substring(genIndex + Config.PACKAGE_SUFFIX.length());
        Profile.getInstance().setDirectorySeparator(DIRECTORY_SEPARATOR);

        int buildIndex = strA.lastIndexOf(DIRECTORY_SEPARATOR + "build" + DIRECTORY_SEPARATOR);
        String MODULE_PATH = strA.substring(0, buildIndex);
        Profile.getInstance().setModulePath(MODULE_PATH);

        int lastDirSplitIndex = MODULE_PATH.lastIndexOf(DIRECTORY_SEPARATOR);
        String MODULE_NAME = MODULE_PATH.substring(lastDirSplitIndex + 1);
        Profile.getInstance().setModuleName(MODULE_NAME);

        XmlParser xmlParser = new XmlParser(Profile.getInstance().getProjectPath() + DIRECTORY_SEPARATOR + CreekXml.CreekXmlName);
        CreekXml.isXmlExists = xmlParser.parse();

        if (!CreekXml.isXmlExists) {
            CreekXml.logDir = Profile.getInstance().getProjectPath()+Profile.getInstance().getDirectorySeparator()+CreekXml.SDKName+"_Log";
            String error = "Exception:there has no CreekRouter.xml in project root Dir(" + Profile.getInstance().getProjectPath() + ")";
            String splitFlag = "********************************************************************************************************************************";
            print(splitFlag);
            print("\n\n\n\n\n");
            printError(error);
            print("\n\n\n\n\n");
            print(splitFlag);
        }

        if (CreekXml.appModule==null || CreekXml.appModule.length()==0){
            String error = "Exception: CreekRouter.xml is not correct";
            String splitFlag = "********************************************************************************************************************************";
            print(splitFlag);
            print("\n\n\n\n\n");
            printError(error);
            print("\n\n\n\n\n");
            print(splitFlag);
        }

    }
}
