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

package com.creek.router.processor;


import com.creek.router.annotation.CreekBean;
import com.creek.router.annotation.CreekClass;
import com.creek.router.annotation.CreekMethod;
import com.creek.router.bean.RouterBean;
import com.creek.router.configure.Profile;
import com.creek.router.generate.java.GenClassBeansImpl;
import com.creek.router.generate.java.GenInstanceCreatorImpl;
import com.creek.router.generate.java.GenMethodBeansImpl;
import com.creek.router.generate.java.GenMethodExeImpl;
import com.creek.router.generate.java.GenMethodProxyImpl;
import com.creek.router.generate.java.GenRoutingTable;
import com.creek.router.generate.java.GenRouterClazzImpl;
import com.creek.router.generate.txt.TxtLogger;
import com.creek.router.generate.txt.TxtWriter;
import com.creek.router.processor.base.BaseProcessor;
import com.google.auto.service.AutoService;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

@AutoService(Processor.class)
public class CoreProcessor extends BaseProcessor {
    private Set<RouterBean> rClassSet = new HashSet<>();
    private Set<RouterBean> rMethodAskSet = new HashSet<>();
    private Set<RouterBean> rMethodImplSet = new HashSet<>();
    private Set<RouterBean> rRouterBeanSet = new HashSet<>();

    @Override
    public Set<String> getScanAnnotation() {
        Set<String> annotationSet = new HashSet<>();
        annotationSet.add(CreekMethod.class.getName());
        annotationSet.add(CreekClass.class.getName());
        annotationSet.add(CreekBean.class.getName());
        return annotationSet;
    }


    @Override
    public void process(RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            GenClassBeansImpl.gen(rClassSet, filerGen);
            GenMethodBeansImpl.gen(rMethodAskSet, rMethodImplSet, filerGen);
            GenRouterClazzImpl.gen(rClassSet, filerGen);
            Map<String, RouterBean> methodProxyMap = GenMethodExeImpl.gen(rMethodImplSet, filerGen);
            GenMethodProxyImpl.gen(methodProxyMap, filerGen);
            GenInstanceCreatorImpl.gen(rRouterBeanSet, filerGen);
            GenRoutingTable.gen(filerGen);//该条必须最后执行
            /* 生成 log 日志*/
            TxtLogger.logTxt(Profile.getInstance().getModuleName(), rClassSet, rRouterBeanSet, rMethodAskSet, rMethodImplSet);
            TxtWriter.writeScanAnnotation(this.rClassSet, this.rRouterBeanSet, this.rMethodAskSet, this.rMethodImplSet);
        } else {
            Set<? extends Element> setMethod = roundEnvironment.getElementsAnnotatedWith(CreekMethod.class);
            Set<? extends Element> setClass = roundEnvironment.getElementsAnnotatedWith(CreekClass.class);
            Set<? extends Element> setRouteBean = roundEnvironment.getElementsAnnotatedWith(CreekBean.class);

            if (setMethod != null && setMethod.size() > 0) {
                for (Element e : setMethod) {
                    RouterBean routerBean = scanMethodAnnotation(e, CreekMethod.class);
                    if (routerBean != null) {
                        if (routerBean.isInterface.equals("1")) {
                            this.rMethodAskSet.add(routerBean);
                        } else {
                            this.rMethodImplSet.add(routerBean);
                        }
                    }
                }
            }
            if (setClass != null && setClass.size() > 0) {
                for (Element e : setClass) {
                    RouterBean routerBean = scanClassAnnotation(e, CreekClass.class);
                    if (routerBean != null) {
                        this.rClassSet.add(routerBean);
                    }
                }
            }
            if (setRouteBean != null && setRouteBean.size() > 0) {
                for (Element e : setRouteBean) {
                    RouterBean routerBean = scanBeanAnnotation(e, CreekBean.class);
                    if (routerBean != null) {
                        this.rRouterBeanSet.add(routerBean);
                    }

                    scanBeanAnnotation(e, CreekBean.class);
                }
            }
        }
    }

    public RouterBean scanBeanAnnotation(Element e, Class<CreekBean> clazz) {
        String pkgName = e.asType().toString();
        if (!e.getKind().name().toLowerCase().equals("class")) {
            return null;
        }
        Set<Modifier> modifiers = e.getModifiers();
        if (modifiers != null) {
            boolean isPublic = false, isAbstract = false;
            for (Modifier m : modifiers) {
                if (m.name().toLowerCase().equals("public")) {
                    isPublic = true;
                }
                if (m.name().toLowerCase().equals("abstract")) {
                    isAbstract = true;
                }
            }
            if (!isPublic || isAbstract) {
                //如果是个接口，将会过滤掉
                return null;
            }
        }
        String path = e.getAnnotation(clazz).path();
        RouterBean routerBean = new RouterBean(path, pkgName, "0");
        return routerBean;
    }

    public RouterBean scanClassAnnotation(Element e, Class<CreekClass> clazz) {

        /*
        需要获取：
        1、注解CreekClass的path值；
        2、注解所在的类的包名；
        3、继承的超类；
        4、继承的接口；
        5、当前clazz是否为接口；
         */
        String path = e.getAnnotation(clazz).path();
        String pkgName = e.asType().toString();

        /*
        合法性检查：
        1、是否修饰class ？
        2、访问权限是否 public、abstract?
        3、是否为interface ?
         */
        if (!e.getKind().name().toLowerCase().equals("class")) {
            return null;
        }
        Set<Modifier> modifiers = e.getModifiers();
        if (modifiers != null) {
            boolean isPublic = false, isAbstract = false;
            for (Modifier m : modifiers) {
                if (m.name().toLowerCase().equals("public")) {
                    isPublic = true;
                }
                if (m.name().toLowerCase().equals("abstract")) {
                    isAbstract = true;
                }
            }
            if (!isPublic || isAbstract) {
                //如果是个接口，将会过滤掉
                return null;
            }
        }

        RouterBean routerBean = new RouterBean(path, pkgName, "0");
        return routerBean;
    }

    public RouterBean scanMethodAnnotation(Element e, Class<CreekMethod> clazz) {
        /*
        合法性检查：
        1、是否修饰method ？
        2、访问权限是否 public?
        3、所在的类必须是 class或者interface ?
         */
        if (!e.getKind().name().toLowerCase().equals("method")) {
            return null;
        }
        boolean isStatic = false, isPublic = false, isAbstract = false;
        Set<Modifier> methodModifiers = e.getModifiers();
        if (methodModifiers != null) {
            for (Modifier m : methodModifiers) {
                if (m.name().toLowerCase().equals("public")) {
                    isPublic = true;
                }
                if (m.name().toLowerCase().equals("abstract")) {
                    isAbstract = true;
                }
                if (m.name().toLowerCase().equals("static")) {
                    isStatic = true;
                }
            }
        }
        if (!isPublic) {
            return null;
        }
        boolean isClass = false, isInterface = false;
        Element classElement = e.getEnclosingElement();
        isClass = classElement.getKind().toString().toLowerCase().equals("class");
        isInterface = classElement.getKind().toString().toLowerCase().equals("interface");
        if (!isClass && !isInterface) {
            return null;
        }
        Set<Modifier> classModifiers = classElement.getModifiers();
        boolean isClsPublic = false, isClsAbstract = false;
        if (classModifiers != null) {
            for (Modifier m : classModifiers) {
                if (m.name().toLowerCase().equals("public")) {
                    isClsPublic = true;
                }
                if (m.name().toLowerCase().equals("abstract")) {
                    isClsAbstract = true;
                }
            }
        }
        if (!isClsPublic) {
            return null;
        }
        if (isClass && isClsAbstract) {
            return null;
        }

        /*
        需要获取：
        1、注解CreekMethod的path值；
        2、注解所在的类的包名；
        3、注解修饰的方法名；
        4、注解修饰方法的返回值的包名；
        5、注解修饰方法的参数的包名；
         */
        String path = e.getAnnotation(clazz).path();
        String pkgName = classElement.asType().toString();
        String method = e.getSimpleName().toString();

        ExecutableElement exe = (ExecutableElement) e;
        String returnType = exe.getReturnType().toString();
        List<? extends VariableElement> params = exe.getParameters();
        List<String> paramsList = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            VariableElement v = params.get(i);
            paramsList.add(v.asType().toString());
        }
        StringBuilder sb_type = new StringBuilder();
        sb_type.append("1");
        sb_type.append(isInterface ? "1" : "0");
        sb_type.append(isStatic ? "0" : "1");

        RouterBean routerBean = new RouterBean(sb_type.toString(), path, pkgName, method, returnType, paramsList, (isInterface ? "1" : "0"));
        return routerBean;
    }
}
