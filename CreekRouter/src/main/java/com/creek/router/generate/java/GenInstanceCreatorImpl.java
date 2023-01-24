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

import com.creek.router.bean.RouterBean;
import com.creek.router.configure.Config;
import com.creek.router.configure.Profile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GenInstanceCreatorImpl {
    public static String CLASS_NAME = "InstanceCreatorImpl";

    public static void gen(Set<RouterBean> routerRouterBeanSet, FilerGen filerGen) {
        Map<String, String> newMapCreate = new HashMap<>();
        Map<String, String> newMapGen = new HashMap<>();
        for (RouterBean routerBean : routerRouterBeanSet) {
            String annotate = routerBean.path;
            String className = routerBean.pkgName;
            String pkgName = className.replace(".", "_") + "_Impl";
            newMapCreate.put(pkgName, annotate);
            newMapGen.put(className, pkgName);
            genCreatorImpl(pkgName, className, filerGen);
        }

        CLASS_NAME = CLASS_NAME + "_" + Profile.getInstance().getAliasModuleName();

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(method_head());
        sb.append(method_body(newMapCreate));
        sb.append(method_tail());

        sb.append(method_head2());
        sb.append(method_body2(newMapGen));
        sb.append(method_tail2());

        sb.append(clazz_tail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);
    }

    private static void genCreatorImpl(String pkgName, String className, FilerGen filerGen) {

        StringBuilder sb = new StringBuilder();
        sb.append("package " + Config.PACKAGE + ";\n\n");
        sb.append("import " + Config.PACKAGE_PROTOCOL + ".*;\n\n");

        sb.append("public class " + pkgName + " implements " + Config.CLAZZ_NAME_DATA_BEAN_CREATOR + " {\n");

        sb.append("    @Override\n");
        sb.append("    public Object createInstance() {\n");
        sb.append("        return " + "new " + className + "();\n");
        sb.append("    }\n");
        sb.append("}\n");
        filerGen.genJavaClass(sb.toString(), pkgName);
    }

    private static String class_import() {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + Config.PACKAGE + ";\n\n");
        sb.append("import " + Config.PACKAGE_PROTOCOL + ".*;\n\n");

        return sb.toString();
    }

    private static String class_head() {
        return "public class " + CLASS_NAME + " implements " + Config.CLAZZ_NAME_INSTANCE_CREATOR + " { \n\n";
    }

    private static String clazz_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String method_head() {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public " + Config.CLAZZ_NAME_DATA_BEAN_CREATOR + " beanCreator(String annotateBeanPath) {\n");
        return sb.toString();
    }

    private static String method_head2() {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public " + Config.CLAZZ_NAME_DATA_BEAN_CREATOR + " beanGenerate(String pkgName) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_tail2() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_body(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();
        for (String pkgName : map.keySet()) {
            sb.append("        if (\"" + map.get(pkgName) + "\".equals(annotateBeanPath)){\n");
            sb.append("            return new " + pkgName + "();\n");
            sb.append("        }\n");
        }
        return sb.toString();
    }

    private static String method_body2(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();
        for (String className : map.keySet()) {
            sb.append("        if (\"" + className + "\".equals(pkgName)){\n");
            sb.append("            return new " + map.get(className) + "();\n");
            sb.append("        }\n");
        }
        return sb.toString();
    }
}
