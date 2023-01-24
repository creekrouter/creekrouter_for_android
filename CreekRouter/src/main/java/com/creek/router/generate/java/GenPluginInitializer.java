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

package com.creek.router.generate.java;

import com.creek.router.configure.Config;
import com.creek.router.configure.Profile;

import java.io.File;
import java.net.URI;
import java.util.Set;

public class GenPluginInitializer {
    public static String CLASS_NAME = Config.PLUGIN_INITIALIZER + "_" + Profile.getInstance().getAliasModuleName();
    public static String FILE_PATH;

    public static void gen(Set<String> set, FilerGen filerGen) {
        URI uri = filerGen.genJavaClass(code(set, CLASS_NAME), CLASS_NAME);
        File file = new File(uri);
        FILE_PATH = file.getAbsolutePath();
    }

    private static String code(Set<String> set, String clsName) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + Config.PACKAGE_MAIN_ROUTE_TABLE + ";\n\n");
        sb.append("public class " + clsName + " {\n");
        sb.append("    static {\n");
        sb.append(class_field(set));
        sb.append("    }\n");
        sb.append("}\n");
        return sb.toString();
    }

    private static String class_field(Set<String> set) {
        StringBuilder sb = new StringBuilder();
        sb.append("        try {\n");
        for (String pkgName : set) {
            sb.append("            Class.forName(\"" + pkgName + "\");\n");
        }
        sb.append("        } catch (Exception e) {\n");
        sb.append("        }\n");
        return sb.toString();
    }
}
