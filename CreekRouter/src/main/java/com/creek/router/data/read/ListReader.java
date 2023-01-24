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

package com.creek.router.data.read;


import com.creek.router.data.RouterParcelable;

import java.util.List;

interface ListReader {
    void readListByte(List<Byte> list);

    void readListShort(List<Short> list);

    void readListInt(List<Integer> list);

    void readListLong(List<Long> list);

    void readListFloat(List<Float> list);

    void readListDouble(List<Double> list);

    void readListChar(List<Character> list);

    void readBool(List<Boolean> list);

    void readListStr(List<String> list);

    void readListObj(List<RouterParcelable> list);

    void readLists(List<List<?>> list);
}
