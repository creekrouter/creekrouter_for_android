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

import java.util.Map;

public class GenMethodProxyImpl {
    public static String CLASS_NAME = "MethodProxyImpl";

    public static void gen(Map<String, RouterBean> methodsImplProxy, FilerGen filerGen) {
        CLASS_NAME = CLASS_NAME + "_" + Profile.getInstance().getAliasModuleName();

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append(method_head());
        sb.append(method_body(methodsImplProxy));
        sb.append(method_tail());

        sb.append(clazz_tail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);
    }

    private static String class_import() {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + Config.PACKAGE + ";\n\n");
        sb.append("import " + Config.PACKAGE_PROTOCOL + ".*;\n\n");

        return sb.toString();
    }

    private static String class_head() {
        return "public class " + CLASS_NAME + " implements " +
                Config.CLAZZ_NAME_METHOD_PROXY + " { \n\n";
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
        sb.append("    public " + Config.CLAZZ_NAME_METHOD_EXECUTOR + " proxy(String annotationPath) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("        return null;\n");
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_body(Map<String, RouterBean> methodsImplProxy) {
        if (methodsImplProxy == null || methodsImplProxy.size() == 0) {
            return "\n";
        }
        StringBuilder sb = new StringBuilder();
        for (String clazz : methodsImplProxy.keySet()) {
            RouterBean routerBean = methodsImplProxy.get(clazz);
            sb.append("        if (\"" + routerBean.path + "\".equals(annotationPath)) {\n");
            sb.append("            return new " + clazz + "();\n");
            sb.append("        }\n");
        }
        return sb.toString();
    }
}
