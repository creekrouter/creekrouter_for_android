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

package com.creek.router.data;


import com.creek.router.main.RoutingTableManager;
import com.creek.router.protocol.DataBeanCreator;

public class DataBeanGetter implements DataBeanCreator {
    private String pkgName;
    private DataBeanCreator proxy;
    private Class<?> clazz;
    private boolean clazzInit = false;

    public DataBeanGetter(String pkgName) {
        this.pkgName = pkgName;
        this.proxy = RoutingTableManager.getInstance().beanGenerate(pkgName);
    }

    private Class<?> getClazz() {
        if (clazz == null && !clazzInit) {
            clazzInit = true;
            try {
                clazz = Class.forName(pkgName);
            } catch (ClassNotFoundException e) {

            }
        }
        return clazz;
    }

    @Override
    public Object createInstance() {
        if (proxy != null) {
            return proxy.createInstance();
        }
        Class<?> cls = getClazz();
        if (cls != null) {
            try {
                Object res = cls.getDeclaredConstructor().newInstance();
                return res;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
