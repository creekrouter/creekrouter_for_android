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

package com.creek.router.tools;


import com.creek.router.configure.Config;
import com.creek.router.configure.CreekXml;
import com.creek.router.configure.Profile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XmlParser {
    private String xmlPath;
    private Document document;
    private Element rootNode;

    public XmlParser(String xmlPath) {
        this.xmlPath = xmlPath;
    }


    public boolean parse() {
        //1.创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(xmlPath);
            rootNode = document.getDocumentElement();
        } catch (Exception e) {

        }
        if (rootNode == null) {
            return false;
        }
        parseMainModule();
        parseModule();
        parse_Log_dir();
        return true;
    }

    private void parseMainModule() {
        CreekXml.appModule = rootNode.getAttribute("AppModule");
        String appDefaultGroup = rootNode.getAttribute("group");
        if (appDefaultGroup == null || appDefaultGroup.length() == 0) {
            CreekXml.appDefaultGroup = Config.MODULE_DEFAULT_GROUP;
        } else {
            CreekXml.appDefaultGroup = appDefaultGroup;
        }
    }

    private void parseModule() {
        NodeList nodeList = rootNode.getElementsByTagName("Module");
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String aliasName = element.getAttribute("name");
                if (Profile.getInstance().getModuleName().equals(aliasName)) {
                    Profile.getInstance().setAliasModuleName(element.getAttribute("aliasName"));
                    Profile.getInstance().setGroup(element.getAttribute("group"));
                    break;
                }
            }
        }
    }

    private void parse_Log_dir() {
        String dir = null;
        if (rootNode != null) {
            NodeList logNodeList = rootNode.getElementsByTagName("Log");
            if (logNodeList != null && logNodeList.getLength() > 0) {
                Element logNode = (Element) logNodeList.item(0);
                dir = logNode.getAttribute("dir");
            }
        }
        String DIR_SEPARATOR = Profile.getInstance().getDirectorySeparator();
        if (dir != null && dir.length() > 0) {
            if (dir.startsWith(".")) {
                dir = dir.replace("/", DIR_SEPARATOR);
                CreekXml.logDir = Profile.getInstance().getProjectPath() + dir.substring(1);
            } else {
                CreekXml.logDir = dir;
            }
        } else {
            String mName = CreekXml.appModule;
            if (mName == null || mName.length() == 0) {
                mName = "CreekLog";
            }
            CreekXml.logDir = Profile.getInstance().getProjectPath() + DIR_SEPARATOR + CreekXml.appModule + DIR_SEPARATOR + "build";
        }
    }

}
