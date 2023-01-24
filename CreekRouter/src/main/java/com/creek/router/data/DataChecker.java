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



import com.creek.router.annotation.CreekBean;
import com.creek.router.annotation.CreekMethod;
import com.creek.router.bean.RouterBean;
import com.creek.router.main.CallBackHandler;
import com.creek.router.main.RoutingTableManager;
import com.creek.router.protocol.CheckResult;
import com.creek.router.protocol.Checker;
import com.creek.router.protocol.DataBeanCreator;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataChecker implements Checker {

    private RouterBean askBean, replyBean;
    private Method askMethod, replyMethod;
    private String annotation;
    private CheckResult result;

    public DataChecker(RouterBean askBean, Method askMethod) {
        this.askBean = askBean;
        this.askMethod = askMethod;
        this.annotation = askMethod.getAnnotation(CreekMethod.class).path();
        this.result = new CheckResult();
    }

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

        if (!returnCheck()) {
            return result;
        }
        Class<?>[] parameters = askMethod.getParameterTypes();
        if (parameterArray == null){
            result.parameterArray = new Object[parameters.length];
            result.isOk = true;
            return result;
        }else {
            if (parameters.length != parameterArray.length) {
                return result;
            }
        }
        result.parameterArray = parameterArray;
        for (int i = 0; i < parameters.length; i++) {
            if (!parameterCheck(askBean.paramsList.get(i), askMethod.getParameterTypes()[i], replyBean.paramsList.get(i), replyMethod.getParameterTypes()[i], i)) {
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


    private boolean returnCheck() {
        if (askBean.returnType.equals(replyBean.returnType)) {
            return true;
        }

        return false;
    }

    private boolean parameterCheck(String askStr, Class<?> askCls, String replyStr, Class<?> replyCls, int index) {
        if (result.parameterArray[index] == null) {
            return true;
        }
        /*
        只能解析：
        1、基本数据类型；
        2、String；
        3、自定义数据类型，Parcelable；
        4、接口；
        以及集合类：
        1、List、ArrayList、LinkedList；
        2、Map、HashMap；
        3、Set、HashSet；
         */
        if (askStr.equals(replyStr)) {
            return true;
        }
        /*
        如果不相等，可能由于：
        1、自定义数据类型，Parcelable；
        2、接口；
        3、泛型。
         */

        /* 查看是否为：CreekParcelable类型. */
        if (isCreekParcelable(askCls) && isCreekParcelable(replyCls)) {
            //转换
            RouterParcelable replyParcelable = createCreekParcelable(replyCls);
            convert((RouterParcelable) result.parameterArray[index], replyParcelable);
            result.parameterArray[index] = replyParcelable;
            return true;
        }

        /*
        集合数据,:
        1、list;
        2、map;
        3、set;
         */
        if (askCls == replyCls) {
            /* 如果两个类型相同，编译时产生的askStr、replyStr却不一样，说明一定有泛型。*/
            return containerConvert(askStr, askCls, replyStr, replyCls, index);
        }

        if (askCls.isInterface() && replyCls.isInterface()) {
            result.parameterArray[index] = Proxy.newProxyInstance(replyCls.getClassLoader(), new Class<?>[]{replyCls},
                    new CallBackHandler(replyCls, result.parameterArray[index], askCls));
            return true;
        }

        return false;
    }


    private boolean containerConvert(String askStr, Class<?> askCls, String replyStr, Class<?> replyCls, int index) {
        /*
        取出泛型里面的 字符串：askStrNew、replyStrNew。
        replyStrNew、replyStrNew 肯定不相等，否则走不到这一步。
        所以，replyStrNew、replyStrNew 只能是 泛型 或者 RouterParcelable类型。
        换句话：如果不包含泛型，又不是RouterParcelable类型，无法进行转换。
         */
        Object methodRes = null;

        String askStrChild = askStr;
        String replyStrChild = replyStr;

        List<String> clsPrefixArr = new ArrayList<>();
        while (askStrChild.contains("<") && replyStrChild.contains("<")) {
            int askIndex = askStr.indexOf("<");
            int replyIndex = askStr.indexOf("<");
            if (askIndex == replyIndex) {
                if (askIndex < 0) {
                    continue;
                }
                String askPrefix = askStrChild.substring(0, askIndex);
                String replyPrefix = replyStrChild.substring(0, replyIndex);

                int askComma = askPrefix.indexOf(",");
                int replyComma = replyPrefix.indexOf(",");
                if (askComma >= 0 || replyComma >= 0) {
                    if (askComma == replyComma) {
                        String askCommaPre = askPrefix.substring(0, askComma);
                        String replyCommaPre = replyPrefix.substring(0, replyComma);
                        if (askCommaPre.equals(replyCommaPre)) {
                            askPrefix = askPrefix.substring(askComma + 1).trim();
                            replyPrefix = replyPrefix.substring(replyComma + 1).trim();
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                if (askPrefix.equals(replyPrefix) && DataType.isJavaUtilContainer(askPrefix)) {
                    clsPrefixArr.add(askPrefix);
                    askStrChild = askStrChild.substring(askIndex + 1, askStrChild.length() - 1);
                    replyStrChild = replyStrChild.substring(replyIndex + 1, replyStrChild.length() - 1);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (askStrChild.contains("<") || replyStrChild.contains("<")) {
            return false;
        }

        DataBeanCreator askCreator = new DataBeanGetter(askStrChild);
        DataBeanCreator replyCreator = new DataBeanGetter(replyStrChild);
        Object askP = askCreator.createInstance();
        Object replyP = replyCreator.createInstance();
        if (RouterParcelable.class.isInstance(askP) && RouterParcelable.class.isInstance(replyP)) {
            //两个类型可以相互转换
            Object aimParam = getConvertRes(result.parameterArray[index], clsPrefixArr, 0, replyCreator);
            if (aimParam != null) {
                result.parameterArray[index] = aimParam;
                return true;
            }
        }

        return false;
    }

    private boolean isCreekParcelable(Class<?> clazz) {
        if (clazz == RouterParcelable.class) {
            return true;
        }
        Class<?>[] interfaceClazzArr = clazz.getInterfaces();
        if (interfaceClazzArr != null && interfaceClazzArr.length > 0) {
            for (Class<?> item : interfaceClazzArr) {
                if (isCreekParcelable(item)) {
                    return true;
                }
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            if (isCreekParcelable(superClazz)) {
                return true;
            }
        }
        return false;
    }

    private RouterParcelable createCreekParcelable(Class<?> replyCls) {
        CreekBean bean = replyCls.getAnnotation(CreekBean.class);
        RouterParcelable obj = null;
        if (bean == null || bean.path().length() == 0) {
            try {
                obj = (RouterParcelable) replyCls.newInstance();
            } catch (Exception e) {
            }
        } else {
            obj = (RouterParcelable) RoutingTableManager.getInstance().beanCreator(bean.path()).createInstance();
        }
        return obj;
    }

    private boolean convert(RouterParcelable source, RouterParcelable des) {
        if (source == null || des == null) {
            return false;
        }
        DataConvert convert = new DataConvert();
        source.routerParcelableRead(convert);
        des.routerParcelableWrite(convert);
        return true;
    }

    private Object getConvertRes(Object sourceContainer, List<String> clsPrefixArr, int arrIndex, DataBeanCreator creator) {
        if (RouterParcelable.class.isInstance(sourceContainer)) {
            Object aimObj = creator.createInstance();
            if (convert((RouterParcelable) sourceContainer, (RouterParcelable) aimObj)) {
                return aimObj;
            }
            return null;
        }
        String pkgName = clsPrefixArr.get(arrIndex);

        if (List.class.getName().equals(pkgName) || ArrayList.class.getName().equals(pkgName)) {
            List sourceList = (List) sourceContainer;
            ArrayList resList = new ArrayList();
            if (sourceList.size() == 0) {
                return resList;
            }
            for (Object obj : sourceList) {
                if (obj == null) {
                    resList.add(obj);
                    continue;
                }
                Object aimObj = getConvertRes(obj, clsPrefixArr, arrIndex + 1, creator);
                if (aimObj == null) {
                    return null;
                }
                resList.add(aimObj);
            }
            return resList;
        } else if (Map.class.getName().equals(pkgName) || HashMap.class.getName().equals(pkgName)) {
            Map sourceMap = (Map) sourceContainer;
            HashMap resMap = new HashMap();
            if (sourceMap.size() == 0) {
                return resMap;
            }
            for (Object key : sourceMap.keySet()) {
                if (sourceMap.get(key) == null) {
                    resMap.put(key, null);
                    continue;
                }
                Object aimObj = getConvertRes(sourceMap.get(key), clsPrefixArr, arrIndex + 1, creator);
                if (aimObj == null) {
                    return null;
                }
                resMap.put(key, aimObj);
            }
            return resMap;
        } else if (Set.class.getName().equals(pkgName) || HashSet.class.getName().equals(pkgName)) {
            Set sourceSet = (Set) sourceContainer;
            HashSet resSet = new HashSet();
            if (sourceSet.size() == 0) {
                return resSet;
            }
            for (Object val : sourceSet) {
                if (val == null) {
                    resSet.add(null);
                    continue;
                }
                Object aimObj = getConvertRes(val, clsPrefixArr, arrIndex + 1, creator);
                if (aimObj == null) {
                    return null;
                }
                resSet.add(aimObj);
            }
            return resSet;
        } else if (LinkedList.class.getName().equals(pkgName)) {
            LinkedList sourceLink = (LinkedList) sourceContainer;
            LinkedList resLink = new LinkedList();
            if (sourceLink.size() == 0) {
                return resLink;
            }
            for (Object obj : sourceLink) {
                if (obj == null) {
                    resLink.add(null);
                    continue;
                }
                Object aimObj = getConvertRes(obj, clsPrefixArr, arrIndex + 1, creator);
                if (aimObj == null) {
                    return null;
                }
                resLink.add(aimObj);
            }
            return resLink;
        }
        return null;
    }
}
