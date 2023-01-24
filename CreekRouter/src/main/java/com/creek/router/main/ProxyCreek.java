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

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;


public class ProxyCreek<T> {
    private CreekHandler handler;
    private T proxy;


    public ProxyCreek(Class<T> service) {
        handler = new CreekHandler(service);
        proxy = (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, handler);
    }

    public CreekHandler getHandler() {
        return handler;
    }

    public T getProxy() {
        return proxy;
    }


    public ProxyCreek<T> withProxyInstance(Object proxyInstance) {
        this.handler.setProxyInstance(proxyInstance);
        return this;
    }

    public ProxyCreek<T> withInterceptor(AnnotateInterceptor interceptor) {
        this.handler.setInterceptor(interceptor);
        return this;
    }

    public ProxyCreek<T> filterFrom(Filters filters, String value) {
        this.handler.setFilterFrom(filters, value);
        return this;
    }

    public ProxyCreek<T> filterTo(Filters filters, String value) {
        this.handler.setFilterTo(filters, value);
        return this;
    }

    public ProxyCreek<T> filterMapFrom(Map<Filters, String> filterMap) {
        if (filterMap == null) {
            return this;
        }
        for (Filters filter : filterMap.keySet()) {
            filterFrom(filter, filterMap.get(filter));
        }
        return this;
    }

    public ProxyCreek<T> filterMapTo(Map<Filters, String> filterMap) {
        if (filterMap == null) {
            return this;
        }
        for (Filters filter : filterMap.keySet()) {
            filterTo(filter, filterMap.get(filter));
        }
        return this;
    }


}
