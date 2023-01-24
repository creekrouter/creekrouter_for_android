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

package com.creek.router.main;


import com.creek.router.Filters;
import com.creek.router.annotation.CreekMethod;
import com.creek.router.bean.RouterBean;
import com.creek.router.data.DataChecker;
import com.creek.router.protocol.Checker;
import com.creek.router.protocol.MethodExecutor;
import com.creek.router.protocol.MethodReturn;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CreekHandler implements InvocationHandler {

    private Class<?> service;
    private Map<String, MethodExecutor> executeMap;
    private Map<String, RouterBean> tableMap;

    private Object proxyInstance;
    private AnnotateInterceptor interceptor;
    private String[] filtersFrom, filtersTo;

    public CreekHandler(Class<?> service) {
        this.service = service;
        this.executeMap = new HashMap<>();
        this.tableMap = new HashMap<>();
        this.filtersFrom = new String[3];
        this.filtersTo = new String[3];
    }

    public void setProxyInstance(Object proxyInstance) {
        this.proxyInstance = proxyInstance;
    }

    public void setInterceptor(AnnotateInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void setFilterFrom(Filters filters, String value) {
        if (filters == Filters.ModuleName) {
            this.filtersFrom[0] = value;
        } else if (filters == Filters.ModuleAliasName) {
            this.filtersFrom[1] = value;

        } else if (filters == Filters.Group) {
            this.filtersFrom[2] = value;
        }
    }

    public void setFilterTo(Filters filters, String value) {
        if (filters == Filters.ModuleName) {
            this.filtersTo[0] = value;
        } else if (filters == Filters.ModuleAliasName) {
            this.filtersTo[1] = value;

        } else if (filters == Filters.Group) {
            this.filtersTo[2] = value;
        }
    }


    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        CreekMethod annotation = method.getAnnotation(CreekMethod.class);
        if (annotation == null) {
            return null;
        }
        String annotationPath = annotation.path();
        if (interceptor != null) {
            annotationPath = interceptor.onIntercept(annotationPath);
        }
        if (annotationPath == null || annotationPath.length() == 0) {
            return null;
        }
        RouterBean askBean = tableMap.get(annotationPath);
        if (askBean == null) {
            askBean = RoutingTableManager.getInstance().methodAskRouterBean(annotationPath, service.getName(), filtersFrom);
            if (askBean == null) {
                return null;
            }
            tableMap.put(annotationPath, askBean);
        }

        MethodExecutor methodProxy = executeMap.get(askBean.path);
        if (methodProxy == null) {
            methodProxy = RoutingTableManager.getInstance().proxy(askBean.path, filtersTo);
            if (methodProxy == null) {
                return null;
            }
            executeMap.put(askBean.path, methodProxy);
        }
        Checker checker = new DataChecker(askBean, method);
        Object result = methodProxy.execute(proxyInstance, checker, args);
        if (result == MethodReturn.ERROR_PARAMETER) {
            // 参数不匹配
            return null;
        }
        return result;
    }
}
