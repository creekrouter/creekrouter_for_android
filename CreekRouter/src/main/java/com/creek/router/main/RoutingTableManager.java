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

import com.creek.router.bean.RouterBean;
import com.creek.router.protocol.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoutingTableManager {
    private static final RoutingTableManager instance = new RoutingTableManager();
    private List<RoutingTable> mTable;
    private Set<String> mRecord;

    private RoutingTableManager() {
        mTable = new ArrayList<>();
        mRecord = new HashSet<>();
    }

    public static RoutingTableManager getInstance() {
        return instance;
    }

    public static void addTable(RoutingTable table) {
        if (table == null) {
            return;
        }
        String tablePkg = table.getClass().getName();
        if (instance.mRecord.contains(tablePkg)) {
            return;
        }
        instance.mRecord.add(tablePkg);
        instance.mTable.add(0, table);
    }

    public List<RoutingTable> getTable() {
        return mTable;
    }

    public Set<String> getRecord() {
        return mRecord;
    }


    public DataBeanCreator beanCreator(String annotateBeanPath, String... filters) {
        for (RoutingTable impl : mTable) {
            DataBeanCreator bean = impl.instanceCreator().beanCreator(annotateBeanPath);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    private List<RoutingTable> getTableListByFilters(String... filters) {
        if (filters == null || filters.length == 0) {
            return this.mTable;
        }

        String moduleName = filters[0];
        String moduleAliasName = filters.length > 1 ? filters[1] : null;
        String group = filters.length > 2 ? filters[2] : null;

        boolean hasName = (moduleName != null && moduleName.length() > 0) || (moduleAliasName != null && moduleAliasName.length() > 0);
        boolean hasGroup = (group != null && group.length() > 0);

        List<RoutingTable> tableList = new ArrayList<>();
        if (hasGroup) {
            for (RoutingTable table : this.mTable) {
                if (table.getGroup().equals(group)) {
                    if (hasName) {
                        if (table.getModuleName().equals(moduleName) || table.getModuleAliasName().equals(moduleAliasName)) {
                            tableList.add(table);
                        }
                    } else {
                        tableList.add(table);
                    }
                }
            }
        } else {
            if (hasName) {
                for (RoutingTable table : this.mTable) {
                    if (table.getModuleName().equals(moduleName) || table.getModuleAliasName().equals(moduleAliasName)) {
                        tableList.add(table);
                    }
                }
            } else {
                return this.mTable;
            }
        }
        return tableList;
    }

    public DataBeanCreator beanGenerate(String pkgName, String... filters) {
        for (RoutingTable moduleTable : getTableListByFilters(filters)) {
            DataBeanCreator bean = moduleTable.instanceCreator().beanGenerate(pkgName);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    public RouterBean methodAskRouterBean(String annotationPath, String pkgName, String... filters) {
        for (RoutingTable moduleTable : getTableListByFilters(filters)) {
            RouterBean bean = moduleTable.methodBeans().methodAskRouterBean(annotationPath, pkgName);
            if (bean != null) {
                return bean;
            }
        }
        return null;
    }

    public MethodExecutor proxy(String annotationPath, String... filters) {
        for (RoutingTable moduleTable : getTableListByFilters(filters)) {
            MethodExecutor methodProxy = moduleTable.methodProxy().proxy(annotationPath);
            if (methodProxy == null) {
                continue;
            }
            return methodProxy;
        }
        return null;
    }

    public Class<?> getClazz(String annotationPath, String... filters) {
        for (RoutingTable moduleTable : getTableListByFilters(filters)) {
            Class<?> clazz = moduleTable.routerClazz().getClazz(annotationPath);
            if (clazz != null) {
                return clazz;
            }
        }
        return null;
    }
}
