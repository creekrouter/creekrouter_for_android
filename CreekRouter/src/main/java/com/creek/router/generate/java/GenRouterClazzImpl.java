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

import com.creek.router.bean.RouterBean;
import com.creek.router.configure.Config;
import com.creek.router.configure.Profile;

import java.util.HashSet;
import java.util.Set;

public class GenRouterClazzImpl {

    public static String CLASS_NAME = "RouterClazzImpl";

    public static void gen(Set<RouterBean> set, FilerGen filerGen) {
        CLASS_NAME = CLASS_NAME + "_" + Profile.getInstance().getAliasModuleName();
        Set<RouterBean> clsSet = getClassSet(set);

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(method_head());
        sb.append(method_body(clsSet));
        sb.append(method_tail());

        sb.append(clazz_tail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);
    }

    private static Set<RouterBean> getClassSet(Set<RouterBean> set) {
        Set<RouterBean> hashSet = new HashSet<>();
        if (set == null || set.size() == 0) {
            return hashSet;
        }
        //确保为class类型的bean
        for (RouterBean routerBean : set) {
            if (routerBean.type.equals("0")) {
                hashSet.add(routerBean);
            }
        }
        return hashSet;
    }

    private static String class_import() {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + Config.PACKAGE + ";\n\n");
        sb.append("import " + Config.PACKAGE_PROTOCOL + ".*;\n\n");

        return sb.toString();
    }

    private static String class_head() {
        return "public class " + CLASS_NAME + " implements RouterClazz { \n\n";
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
        sb.append("    public Class<?> getClazz(String annotationPath) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n");
        return sb.toString();
    }

    private static String method_body(Set<RouterBean> set) {
        if (set == null || set.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();

        for (RouterBean routerBean : set) {
            sb.append(create_sentence(routerBean));
            sb.append("\n");
        }
        return sb.toString();
    }

    private static String create_sentence(RouterBean routerBean) {
        StringBuilder sb = new StringBuilder();
        sb.append("        if (annotationPath.equals(\"" + routerBean.path + "\")) {\n");
        sb.append("            return " + routerBean.pkgName + ".class;\n");
        sb.append("        }\n");
        return sb.toString();
    }

}
