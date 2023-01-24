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

package com.creek.router.configure;

import com.creek.router.annotation.CreekFlag;
import com.creek.router.bean.RouterBean;
import com.creek.router.protocol.*;


public class Config {
    /*
    gen java code
     */
    public static final String PACKAGE_SUFFIX = "genCode";
    public static final String PACKAGE = "com.creek." + PACKAGE_SUFFIX;
    public static final String ROUTING_TABLE_PREFIX = PACKAGE + ".RoutingTable_";


    /*
    annotation
     */
    public static final String TEMP_FLAG_CLASS_NAME = "RouteFlagClass";
    public static final String PACKAGE_ANNOTATION = CreekFlag.class.getPackage().getName();
    public static final String ANNOTATION_CREEK_FLAG = CreekFlag.class.getSimpleName();


    /*
    bean
     */
    public static final String PACKAGE_BEAN = RouterBean.class.getPackage().getName();
    public static final String CLAZZ_NAME_ROUTER_BEAN = RouterBean.class.getSimpleName();
    public static final String METHOD_NAME_ROUTER_BEAN_CREATE = "create";


    /*
    protocol
     */
    public static final String PACKAGE_PROTOCOL = RoutingTable.class.getPackage().getName();
    public static final String CLAZZ_NAME_CREEK_INTERFACE = RoutingTable.class.getSimpleName();

    public static final String CLAZZ_NAME_IMPL_ADDER = RoutingTableTools.class.getSimpleName();
    public static final String METHOD_NAME_IMPL_ADDER = "addTable";

    public static final String CLAZZ_NAME_CLASS_BEANS = ClassBeans.class.getSimpleName();
    public static final String METHOD_NAME_CLASS_BEANS = "classRouterBean";

    public static final String CLAZZ_NAME_DATA_BEAN_CREATOR = DataBeanCreator.class.getSimpleName();

    public static final String CLAZZ_NAME_INSTANCE_CREATOR = InstanceCreator.class.getSimpleName();

    public static final String CLAZZ_NAME_METHOD_BEANS = MethodBeans.class.getSimpleName();

    public static final String CLAZZ_NAME_METHOD_EXECUTOR = MethodExecutor.class.getSimpleName();

    public static final String CLAZZ_NAME_METHOD_PROXY = MethodProxy.class.getSimpleName();

    public static final String CLAZZ_NAME_ROUTER_CLAZZ = RouterClazz.class.getSimpleName();

    public static final String MODULE_DEFAULT_GROUP = "default";


    /*
     routing table Initializer
     */
    public static final String PACKAGE_MAIN_ROUTE_TABLE = "com.creek.router.init";
    public static final String INITIALIZER = "Initializer";
    public static final String PLUGIN_INITIALIZER = "Plugin_Initializer";


    public static final String ApacheLicense = "/*\n" +
            " * Copyright (C) 2010 The Android Open Source Project\n" +
            " *\n" +
            " * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
            " * you may not use this file except in compliance with the License.\n" +
            " * You may obtain a copy of the License at\n" +
            " *\n" +
            " *      http://www.apache.org/licenses/LICENSE-2.0\n" +
            " *\n" +
            " * Unless required by applicable law or agreed to in writing, software\n" +
            " * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
            " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
            " * See the License for the specific language governing permissions and\n" +
            " * limitations under the License.\n" +
            " */\n\n";
    public static final String AuthorInfo = "/*\n" +
            " * CreekRouter\n" +
            " * Author:LvXiaofei\n" +
            " * Email:creekrouter@163.com\n" +
            " */\n\n";
}
