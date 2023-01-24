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

public class Profile {
    private String projectPath;
    private String moduleName;
    private String modulePath;
    private String directorySeparator;
    private String aliasModuleName;
    private String group;

    private static Profile profile = new Profile();

    private Profile() {
        projectPath = System.getProperty("user.dir");

    }

    public static Profile getInstance() {
        return profile;
    }


    public String getProjectPath() {
        return projectPath;
    }


    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getAliasModuleName() {
        if (aliasModuleName == null || aliasModuleName.length() == 0) {
            return moduleName;
        }
        return aliasModuleName;
    }

    public void setAliasModuleName(String aliasModuleName) {
        this.aliasModuleName = aliasModuleName;
    }

    public String getModulePath() {
        return modulePath;
    }

    public void setModulePath(String modulePath) {
        this.modulePath = modulePath;
    }

    public String getDirectorySeparator() {
        return directorySeparator;
    }

    public void setDirectorySeparator(String directorySeparator) {
        this.directorySeparator = directorySeparator;
    }

    public String getGroup() {
        if (group != null && group.length() > 0) {
            return group;
        }
        return CreekXml.appDefaultGroup == null ? Config.MODULE_DEFAULT_GROUP : CreekXml.appDefaultGroup;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
