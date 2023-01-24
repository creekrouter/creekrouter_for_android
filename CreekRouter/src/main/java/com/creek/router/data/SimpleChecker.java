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

package com.creek.router.data;

import com.creek.router.annotation.CreekMethod;
import com.creek.router.bean.RouterBean;
import com.creek.router.protocol.CheckResult;
import com.creek.router.protocol.Checker;

import java.lang.reflect.Method;

public class SimpleChecker implements Checker {

    private String annotation;
    private CheckResult result;
    private RouterBean replyBean;
    private Method replyMethod;

    public SimpleChecker(String annotatePath) {
        this.annotation = annotatePath;
        this.result = new CheckResult();
    }

    /* 制作参数类型、参数个数 检查，不检查泛型 */
    @Override
    public CheckResult methodCheck(RouterBean replyBean, Class<?> replyClazz, Object... parameterArray) {
        result.isOk = false;
        if (annotation == null || annotation.length() == 0) {
            return result;
        }

        this.replyBean = replyBean;
        for (Method m : replyClazz.getMethods()) {
            CreekMethod replyAnnotate = m.getAnnotation(CreekMethod.class);
            if (replyAnnotate != null && annotation.equals(replyAnnotate.path())) {
                replyMethod = m;
                break;
            }
        }

        if (replyMethod == null) {
            return result;
        }
        /* 方法返回值 无法检查 */

        Class<?>[] parameters = replyMethod.getParameterTypes();
        if (parameterArray == null) {
            result.parameterArray = new Object[parameters.length];
            result.isOk = true;
            return result;
        } else {
            if (parameters.length != parameterArray.length) {
                return result;
            }
        }
        result.parameterArray = parameterArray;

        for (int i = 0; i > parameters.length; i++) {
            if (parameterArray[i] == null) {
                continue;
            }
            Class<?> clazzParam = parameters[i];
            if (!clazzParam.isInstance(parameterArray[i])) {
                return result;
            }
        }
        result.isOk = true;
        return result;
    }

    @Override
    public boolean instanceCheck(Object instance, Class<?> clazz) {
        if (instance != null && clazz.isInstance(instance)) {
            return true;
        }
        return false;
    }
}
