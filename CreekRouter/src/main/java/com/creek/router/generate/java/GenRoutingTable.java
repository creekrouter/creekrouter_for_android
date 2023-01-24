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

package com.creek.router.generate.java;


import com.creek.router.configure.Config;
import com.creek.router.configure.Profile;

import java.io.File;
import java.net.URI;

public class GenRoutingTable {
    public static String CLASS_NAME = "RoutingTable";
    public static String FILE_PATH;
    public static boolean execute = false;

    public static void gen(FilerGen filerGen) {
        CLASS_NAME = CLASS_NAME + "_" + Profile.getInstance().getAliasModuleName();

        execute = true;

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(static_code());
        sb.append(method());

        sb.append(clazz_tail());
        URI uri = filerGen.genJavaClass(sb.toString(), CLASS_NAME);
        File file = new File(uri);
        FILE_PATH = file.getAbsolutePath();
    }

    private static String static_code() {
        StringBuilder sb = new StringBuilder();
        sb.append("    public static final String moduleName = \"" + Profile.getInstance().getModuleName() + "\";\n");
        sb.append("    public static final String moduleAliasName = \"" + Profile.getInstance().getAliasModuleName() + "\";\n");
        sb.append("    public static final String group = \"" + Profile.getInstance().getGroup() + "\";\n\n");

        sb.append("    static {\n");
        sb.append("        " + Config.CLAZZ_NAME_IMPL_ADDER + "." + Config.METHOD_NAME_IMPL_ADDER + "(new " + CLASS_NAME + "());\n");
        sb.append("    }\n\n");

        return sb.toString();
    }

    private static String class_import() {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + Config.PACKAGE + ";\n\n");
        sb.append("import " + Config.PACKAGE_PROTOCOL + ".*;\n");
        sb.append("import " + Config.PACKAGE_BEAN + ".*;\n\n");
        return sb.toString();
    }

    private static String class_head() {
        return "public class " +
                CLASS_NAME +
                " implements " + Config.CLAZZ_NAME_CREEK_INTERFACE + " { \n\n";
    }

    private static String clazz_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String method() {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public " + Config.CLAZZ_NAME_CLASS_BEANS + " classBeans() {\n");
        sb.append("        return new " + Config.PACKAGE + "." + GenClassBeansImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public " + Config.CLAZZ_NAME_METHOD_BEANS + " methodBeans() {\n");
        sb.append("        return new " + Config.PACKAGE + "."  + GenMethodBeansImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public " + Config.CLAZZ_NAME_ROUTER_CLAZZ + " routerClazz() {\n");
        sb.append("        return new " + Config.PACKAGE + "."  + GenRouterClazzImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public " + Config.CLAZZ_NAME_METHOD_PROXY + " methodProxy() {\n");
        sb.append("        return new " + Config.PACKAGE + "."  + GenMethodProxyImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public " + Config.CLAZZ_NAME_INSTANCE_CREATOR + " instanceCreator() {\n");
        sb.append("        return new " + Config.PACKAGE + "."  + GenInstanceCreatorImpl.CLASS_NAME + "();\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public String getModuleName() {\n");
        sb.append("        return moduleName;\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public String getModuleAliasName() {\n");
        sb.append("        return moduleAliasName;\n");
        sb.append("    }\n\n");

        sb.append("    @Override\n");
        sb.append("    public String getGroup() {\n");
        sb.append("        return group;\n");
        sb.append("    }\n\n");

        return sb.toString();
    }
}
