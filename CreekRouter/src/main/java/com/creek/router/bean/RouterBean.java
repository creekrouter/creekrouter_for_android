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

package com.creek.router.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RouterBean {
    public final String path;
    public final String pkgName;
    public final String method;
    public final String returnType;
    public final List<String> paramsList;
    public final String isInterface;
    public final String type;

     /*
    结合 RouterBean 看。

   String type = 0; 代表　类
   String type = 1; 代表　方法
   String type = 2; 代表　静态变量/类变量

   对于 type=1 方法，进行位数扩展：
   第 2 位，0 代表 该方法有实现体， 1 代表 这是一个 interface;
   第 3 位，0 代表 该方法为静态 static；
    */

    public RouterBean(String type, String path, String pkgName, String method, String returnType, List<String> pList, String isInterface) {
        this.path = path;
        this.pkgName = pkgName;
        this.method = method;
        this.returnType = returnType;
        if (pList == null) {
            this.paramsList = new ArrayList<>();
        } else {
            this.paramsList = pList;
        }
        this.isInterface = isInterface;
        this.type = type;
    }


    public RouterBean(String path, String pkgName, String isInterface) {
        this.path = path;
        this.pkgName = pkgName;
        this.method = "";
        this.returnType = "";
        this.paramsList = new ArrayList<>();
        this.isInterface = isInterface;
        this.type = "0";
    }

    public static RouterBean create(String type, String isInterface, String path, String pkgName, String method, String returnType, String... params) {
        List<String> paramsList = new ArrayList<>();
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                paramsList.add(params[i]);
            }
        }
        return new RouterBean(type, path, pkgName, method, returnType, paramsList, isInterface);
    }

    public boolean isStaticMethodImpl() {
        return "100".equals(type);
    }

    @Override
    public String toString() {
        String str = "path=" + path + '#' +
                "pkgName=" + pkgName + '#' +
                "method=" + method + '#' +
                "returnType=" + returnType + '#' +
                "paramsList=";
        if (this.paramsList != null && this.paramsList.size() > 0) {
            for (int i = 0; i < this.paramsList.size(); i++) {
                String param = this.paramsList.get(i);
                str = str + param + "-";
            }
        }
        str = str + "#" + "isInterface=" + isInterface;
        str = str + "#" + "type=" + type;
        return str;

    }

    public static RouterBean convertStrToBean(String toString) {
        String[] all = toString.split("#");
        String path_tmp = all[0];
        String pkgName_tmp = all[1];
        String method_tmp = all[2];
        String returnType_tmp = all[3];
        String paramsList_tmp = all[4];
        String isInterface_tmp = all[5];
        String type_tmp = all[6];

        String path = path_tmp.substring(path_tmp.indexOf("=") + 1);
        String pkgName = pkgName_tmp.substring(pkgName_tmp.indexOf("=") + 1);
        String method = method_tmp.substring(method_tmp.indexOf("=") + 1);
        String returnType = returnType_tmp.substring(returnType_tmp.indexOf("=") + 1);
        String paramsList = paramsList_tmp.substring(paramsList_tmp.indexOf("=") + 1);
        String isInterface = isInterface_tmp.substring(isInterface_tmp.indexOf("=") + 1);
        String type = type_tmp.substring(type_tmp.indexOf("=") + 1);

        if (type == null || type.length() == 0) {
            return null;
        }
        RouterBean routerBean = null;
        if (type.startsWith("0")) {
            routerBean = new RouterBean(path, pkgName, isInterface);
        } else if (type.startsWith("1")) {
            String[] paramsArr = paramsList.split("-");
            List<String> list = new ArrayList<>();
            if (paramsArr != null && paramsArr.length > 0) {
                for (int i = 0; i < paramsArr.length; i++) {
                    list.add(paramsArr[i]);
                }
            }
            routerBean = new RouterBean(type, path, pkgName, method, returnType, list, isInterface);
        }
        return routerBean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouterBean routerBean = (RouterBean) o;
        return path.equals(routerBean.path) &&
                pkgName.equals(routerBean.pkgName) &&
                method.equals(routerBean.method) &&
                returnType.equals(routerBean.returnType) &&
                paramsList.equals(routerBean.paramsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, pkgName, method, returnType, paramsList);
    }
}
