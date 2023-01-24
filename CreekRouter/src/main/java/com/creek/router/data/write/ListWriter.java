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

package com.creek.router.data.write;


import com.creek.router.data.RouterParcelable;

import java.util.List;

interface ListWriter {
    void writeListByte(List<Byte> list);

    void writeListShort(List<Short> list);

    void writeListInt(List<Integer> list);

    void writeListLong(List<Long> list);

    void writeListFloat(List<Float> list);

    void writeListDouble(List<Double> list);

    void writeListChar(List<Character> list);

    void writeBool(List<Boolean> list);

    void writeListStr(List<String> list);

    void writeListObj(List<RouterParcelable> list);

    void writeLists(List<List<?>> list);
}
