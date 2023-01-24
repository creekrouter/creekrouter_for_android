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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GenMethodExeImpl {
    private static String CLASS_NAME = "MethodExecutorImpl";
    private static final String Pre_Name = "MethodExecutorImpl";

    private static long method_index = 0;

    public static Map<String, RouterBean> gen(Set<RouterBean> set, FilerGen filerGen) {

        Map<String, RouterBean> exeClazz = new HashMap<>();
        if (set == null) {
            return exeClazz;
        }
        for (RouterBean routerBean : set) {
            genEveryOne(routerBean, filerGen, exeClazz);
        }

        return exeClazz;
    }

    private static void genEveryOne(RouterBean routerBean, FilerGen filerGen, Map<String, RouterBean> exeClazz) {
        CLASS_NAME = Pre_Name + "_" + Profile.getInstance().getAliasModuleName() + "_" + method_index;
        method_index++;
        exeClazz.put(CLASS_NAME, routerBean);

        StringBuilder sb = new StringBuilder();
        sb.append(class_import());
        sb.append(class_head());

        sb.append("    /*\n");
        sb.append("        Annotation Path = ");
        sb.append(routerBean.path + "\n");
        sb.append("    */\n\n");
        sb.append(clazz_field(routerBean));

        sb.append(method_head());
        sb.append(method_body(routerBean));
        sb.append(method_tail());

        sb.append(clazz_tail());
        filerGen.genJavaClass(sb.toString(), CLASS_NAME);

    }

    private static String class_import() {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + Config.PACKAGE + ";\n\n");
        sb.append("import " + Config.PACKAGE_PROTOCOL + ".*;\n");
        sb.append("import " + Config.PACKAGE_BEAN + ".*;\n\n");

        return sb.toString();
    }

    private static String class_head() {
        return "public class " + CLASS_NAME + " implements " + Config.CLAZZ_NAME_METHOD_EXECUTOR + " { \n\n";
    }

    private static String clazz_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String clazz_field(RouterBean routerBean) {
        StringBuilder sb = new StringBuilder();
        sb.append("    public final " + Config.CLAZZ_NAME_ROUTER_BEAN + " bean = ");
        sb.append(Config.CLAZZ_NAME_ROUTER_BEAN + "." + Config.METHOD_NAME_ROUTER_BEAN_CREATE + "(" +
                "\"" + routerBean.type + "\", " +
                "\"" + routerBean.isInterface + "\", " +
                "\"" + routerBean.path + "\", " +
                "\"" + routerBean.pkgName + "\", \"" +
                routerBean.method + "\", \"" +
                routerBean.returnType + "\"");
        for (int i = 0; i < routerBean.paramsList.size(); i++) {
            sb.append(", \"" + routerBean.paramsList.get(i) + "\"");
        }
        sb.append(");\n\n");
        return sb.toString();
    }

    private static String method_head() {
        StringBuilder sb = new StringBuilder();
        sb.append("    @Override\n");
        sb.append("    public Object execute(Object instance, Checker checker, Object... parameters) {\n");
        return sb.toString();
    }

    private static String method_tail() {
        StringBuilder sb = new StringBuilder();
        sb.append("    }\n\n");
        return sb.toString();
    }

    private static String method_body(RouterBean routerBean) {
        StringBuilder sb = new StringBuilder();
        sb.append("        CheckResult result = checker.methodCheck(bean, " + routerBean.pkgName + ".class, parameters);\n");
        sb.append("        if (!result.isOk) {\n");
        sb.append("            return MethodReturn.ERROR_PARAMETER;\n");
        sb.append("        }\n");
        sb.append(create_sentence(routerBean));
        return sb.toString();
    }


    private static String create_sentence(RouterBean routerBean) {
        StringBuilder sb = new StringBuilder();
        boolean voidReturn = "void".equals(routerBean.returnType.toLowerCase());
        if (routerBean.isStaticMethodImpl()) {
            if (voidReturn) {
                sb.append("        " + routerBean.pkgName + "." + routerBean.method + "(");
            } else {
                sb.append("        return " + routerBean.pkgName + "." + routerBean.method + "(");
            }
        } else {
            sb.append("        " + routerBean.pkgName + " proxyInstance = null;\n");
            sb.append("        if (checker.instanceCheck(instance, " + routerBean.pkgName + ".class)) {\n");
            sb.append("            proxyInstance = (" + routerBean.pkgName + ") instance;\n");
            sb.append("        }else {\n");
            sb.append("            proxyInstance = new " + routerBean.pkgName + "();\n");
            sb.append("        }\n");
            if (voidReturn) {
                sb.append("        proxyInstance." + routerBean.method + "(");
            } else {
                sb.append("        return proxyInstance." + routerBean.method + "(");
            }
        }
        for (int i = 0; i < routerBean.paramsList.size(); i++) {
            sb.append("(" + routerBean.paramsList.get(i) + ") result.parameterArray[" + i + "]");
            if (i < routerBean.paramsList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(");\n");
        if (voidReturn) {
            sb.append("        return null;\n");
        }
        return sb.toString();
    }
}
