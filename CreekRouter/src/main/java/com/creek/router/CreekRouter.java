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

package com.creek.router;


import com.creek.router.configure.Config;
import com.creek.router.data.SimpleChecker;
import com.creek.router.main.ProxyCreek;
import com.creek.router.main.RoutingTableManager;
import com.creek.router.protocol.DataBeanCreator;
import com.creek.router.protocol.MethodExecutor;

import java.util.HashMap;
import java.util.Map;

public final class CreekRouter {
    static {
        try {
            Class.forName(Config.PACKAGE_MAIN_ROUTE_TABLE + "." + Config.INITIALIZER);
        } catch (Exception e) {

        }
    }

    public static <T> T create(Class<T> service, Object proxyInstance, Map<Filters, String> filterFrom, Map<Filters, String> filterTo) {
        return new ProxyCreek<>(service).
                withProxyInstance(proxyInstance).
                filterMapFrom(filterFrom).
                filterMapTo(filterTo).
                getProxy();
    }

    public static <T> T create(Class<T> service, Map<Filters, String> filterFrom, Map<Filters, String> filterTo) {
        return create(service, null, filterFrom, filterTo);
    }

    public static <T> T create(Class<T> service, Object proxyInstance, String groupFrom, String groupTo) {
        return new ProxyCreek<>(service).
                withProxyInstance(proxyInstance).
                filterFrom(Filters.Group, groupFrom).
                filterTo(Filters.Group, groupTo).
                getProxy();
    }

    public static <T> T create(Class<T> service, Object proxyInstance) {
        return new ProxyCreek<>(service).withProxyInstance(proxyInstance).getProxy();
    }


    public static <T> T create(Class<T> service) {
        return create(service, null);
    }

    public static Object methodRun(String annotatePath, Object... parameters) {
        MethodExecutor executor = RoutingTableManager.getInstance().proxy(annotatePath);
        return executor == null ? null : executor.execute(null, new SimpleChecker(annotatePath), parameters);
    }

    public static Object methodExe(String annotatePath, String groupTo, Object... parameters) {
        return methodInvoke(annotatePath, null, groupTo, parameters);
    }

    public static Object methodCall(String annotatePath, Object instance, Object... parameters) {
        return methodInvoke(annotatePath, instance, null, parameters);
    }

    public static Object methodInvoke(String annotatePath, Object instance, String groupTo, Object... parameters) {
        if (groupTo == null || groupTo.length() == 0) {
            return methodCarryout(annotatePath, instance, null, parameters);
        }
        Map<Filters, String> filterMapTo = new HashMap<>();
        filterMapTo.put(Filters.Group, groupTo);
        return methodCarryout(annotatePath, instance, filterMapTo, parameters);
    }

    public static Object methodCarryout(String annotatePath, Object instance, Map<Filters, String> filterMapTo, Object... args) {
        MethodExecutor executor = null;
        if (filterMapTo == null) {
            executor = RoutingTableManager.getInstance().proxy(annotatePath);
        } else {
            String moduleName = filterMapTo.get(Filters.ModuleName);
            String moduleAliasName = filterMapTo.get(Filters.ModuleAliasName);
            String group = filterMapTo.get(Filters.Group);
            executor = RoutingTableManager.getInstance().proxy(annotatePath, moduleName, moduleAliasName, group);
        }
        return executor == null ? null : executor.execute(instance, new SimpleChecker(annotatePath), args);
    }


    public static Class<?> getClazz(String annotateClazzPath) {
        return getClazz(annotateClazzPath, null);
    }

    public static Class<?> getClazz(String annotateClazzPath, Map<Filters, String> filterMap) {
        if (filterMap == null) {
            return RoutingTableManager.getInstance().getClazz(annotateClazzPath);
        } else {
            String moduleName = filterMap.get(Filters.ModuleName);
            String moduleAliasName = filterMap.get(Filters.ModuleAliasName);
            String group = filterMap.get(Filters.Group);
            return RoutingTableManager.getInstance().getClazz(annotateClazzPath, moduleName, moduleAliasName, group);
        }
    }

    public static Object getBean(String annotateBeanPath) {
        return getBean(annotateBeanPath, null);
    }

    public static Object getBean(String annotateBeanPath, Map<Filters, String> filterMap) {
        DataBeanCreator creator = beanCreator(annotateBeanPath, filterMap);
        return creator == null ? null : creator.createInstance();
    }

    public static DataBeanCreator beanCreator(String annotateBeanPath) {
        return beanCreator(annotateBeanPath, null);
    }

    public static DataBeanCreator beanCreator(String annotateBeanPath, Map<Filters, String> filterMap) {
        if (filterMap == null) {
            return RoutingTableManager.getInstance().beanCreator(annotateBeanPath);
        } else {
            String moduleName = filterMap.get(Filters.ModuleName);
            String moduleAliasName = filterMap.get(Filters.ModuleAliasName);
            String group = filterMap.get(Filters.Group);
            return RoutingTableManager.getInstance().beanCreator(annotateBeanPath, moduleName, moduleAliasName, group);
        }
    }

}
