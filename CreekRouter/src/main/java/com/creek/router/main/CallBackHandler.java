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

package com.creek.router.main;

import com.creek.router.annotation.CreekMethod;
import com.creek.router.bean.RouterBean;
import com.creek.router.data.DataChecker;
import com.creek.router.protocol.CheckResult;
import com.creek.router.protocol.Checker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CallBackHandler implements InvocationHandler {
    private Class<?> service;
    private Object proxyInstance;
    private Class<?> proxyClazz;


    public CallBackHandler(Class<?> service, Object proxyInstance, Class<?> proxyClazz) {
        this.service = service;
        this.proxyInstance = proxyInstance;
        this.proxyClazz = proxyClazz;
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
        if (annotationPath == null || annotationPath.length() == 0) {
            return null;
        }
        Method methodReply = null;
        for (Method m : proxyClazz.getMethods()) {
            CreekMethod routerMethod = m.getAnnotation(CreekMethod.class);
            if (routerMethod != null && annotationPath.equals(routerMethod.path())) {
                methodReply = proxyInstance.getClass().getMethod(m.getName(), m.getParameterTypes());
            }
        }
        if (methodReply == null) {
            return null;
        }
        RouterBean askBean = RoutingTableManager.getInstance().methodAskRouterBean(annotationPath, service.getName());
        if (askBean == null) {
            return null;
        }
        RouterBean replyBean = RoutingTableManager.getInstance().methodAskRouterBean(annotationPath, proxyClazz.getName());
        if (replyBean == null) {
            return null;
        }
        Checker checker = new DataChecker(askBean, method);
        CheckResult result = checker.methodCheck(replyBean, proxyClazz, args);
        if (result.isOk) {
            return methodReply.invoke(proxyInstance, result.parameterArray);
        }
        return null;

    }
}
