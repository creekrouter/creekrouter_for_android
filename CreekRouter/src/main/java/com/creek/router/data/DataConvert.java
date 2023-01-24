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

import com.creek.router.data.read.Reader;
import com.creek.router.data.write.Writer;

import java.util.HashMap;
import java.util.List;

class DataConvert implements Reader, Writer {
    private int readIndex = -1;
    private int writeIndex = -1;
    private HashMap<Integer, Byte> byteMap;
    private HashMap<Integer, Short> shortMap;
    private HashMap<Integer, Integer> intMap;
    private HashMap<Integer, Long> longMap;
    private HashMap<Integer, Float> floatMap;
    private HashMap<Integer, Double> doubleMap;
    private HashMap<Integer, Character> charMap;
    private HashMap<Integer, Boolean> booleanMap;
    private HashMap<Integer, String> strMap;
    private HashMap<Integer, RouterParcelable> objMap;
    private HashMap<Integer, List> listBasicMap;
    private HashMap<Integer, List<RouterParcelable>> listObjMap;
    private HashMap<Integer, List<List<?>>> listsMap;

    protected DataConvert() {
        readIndex = -1;
        writeIndex = -1;
    }


    @Override
    public void read(byte val_byte) {
        if (byteMap == null) {
            byteMap = new HashMap<>();
        }
        byteMap.put(getReadIndex(), val_byte);
    }

    @Override
    public void read(short val_short) {
        if (shortMap == null) {
            shortMap = new HashMap<>();
        }
        shortMap.put(getReadIndex(), val_short);
    }

    @Override
    public void read(int val_int) {
        if (intMap == null) {
            intMap = new HashMap<>();
        }
        intMap.put(getReadIndex(), val_int);
    }

    @Override
    public void read(long val_long) {
        if (longMap == null) {
            longMap = new HashMap<>();
        }
        longMap.put(getReadIndex(), val_long);
    }

    @Override
    public void read(float val_float) {
        if (floatMap == null) {
            floatMap = new HashMap<>();
        }
        floatMap.put(getReadIndex(), val_float);
    }

    @Override
    public void read(double val_double) {
        if (doubleMap == null) {
            doubleMap = new HashMap<>();
        }
        doubleMap.put(getReadIndex(), val_double);
    }

    @Override
    public void read(char val_char) {
        if (charMap == null) {
            charMap = new HashMap<>();
        }
        charMap.put(getReadIndex(), val_char);
    }

    @Override
    public void read(boolean val_bool) {
        if (booleanMap == null) {
            booleanMap = new HashMap<>();
        }
        booleanMap.put(getReadIndex(), val_bool);
    }

    @Override
    public void read(String val_str) {
        if (strMap == null) {
            strMap = new HashMap<>();
        }
        strMap.put(getReadIndex(), val_str);
    }

    @Override
    public void read(RouterParcelable val_obj) {
        if (objMap == null) {
            objMap = new HashMap<>();
        }
        objMap.put(getReadIndex(), val_obj);
    }

    private int getReadIndex() {
        readIndex++;
        return readIndex;
    }

    private int getWriteIndex() {
        writeIndex++;
        return writeIndex;
    }

    @Override
    public byte writeByte() {
        if (byteMap == null || byteMap.size() == 0) {
            return 0;
        }
        return byteMap.get(getWriteIndex());
    }

    @Override
    public short writeShort() {
        if (shortMap == null || shortMap.size() == 0) {
            return 0;
        }
        return shortMap.get(getWriteIndex());
    }

    @Override
    public int writeInt() {
        if (intMap == null || intMap.size() == 0) {
            return 0;
        }
        return intMap.get(getWriteIndex());
    }

    @Override
    public long writeLong() {
        if (longMap == null || longMap.size() == 0) {
            return 0l;
        }
        return longMap.get(getWriteIndex());
    }

    @Override
    public float writeFloat() {
        if (floatMap == null || floatMap.size() == 0) {
            return 0f;
        }
        return floatMap.get(getWriteIndex());
    }

    @Override
    public double writeDouble() {
        if (doubleMap == null || doubleMap.size() == 0) {
            return 0d;
        }
        return doubleMap.get(getWriteIndex());
    }

    @Override
    public char writeChar() {
        if (charMap == null || charMap.size() == 0) {
            return '\0';
        }
        return charMap.get(getWriteIndex());
    }

    @Override
    public boolean writeBoolean() {
        if (booleanMap == null || booleanMap.size() == 0) {
            return false;
        }
        return booleanMap.get(getWriteIndex());
    }

    @Override
    public String writeStr() {
        if (strMap == null || strMap.size() == 0) {
            return "";
        }
        return strMap.get(getWriteIndex());
    }

    @Override
    public void writeObj(RouterParcelable desData) {
        if (objMap == null || objMap.size() == 0 || desData == null) {
            return;
        }
        RouterParcelable source = objMap.get(getWriteIndex());
        if (source == null) {
            return;
        }
        DataConvert convert = new DataConvert();
        source.routerParcelableRead(convert);
        desData.routerParcelableWrite(convert);
    }

    private void putListBasicIntoMap(List<?> list) {
        if (listBasicMap == null) {
            listBasicMap = new HashMap<>();
        }
        listBasicMap.put(getReadIndex(), list);
    }

    @Override
    public void readListByte(List<Byte> list) {
        putListBasicIntoMap(list);
    }

    @Override
    public void readListShort(List<Short> list) {
        putListBasicIntoMap(list);
    }

    @Override
    public void readListInt(List<Integer> list) {
        putListBasicIntoMap(list);
    }

    @Override
    public void readListLong(List<Long> list) {
        putListBasicIntoMap(list);
    }

    @Override
    public void readListFloat(List<Float> list) {
        putListBasicIntoMap(list);
    }

    @Override
    public void readListDouble(List<Double> list) {
        putListBasicIntoMap(list);
    }

    @Override
    public void readListChar(List<Character> list) {
        putListBasicIntoMap(list);
    }

    @Override
    public void readBool(List<Boolean> list) {
        putListBasicIntoMap(list);
    }

    @Override
    public void readListStr(List<String> list) {
        putListBasicIntoMap(list);
    }

    @Override
    public void readListObj(List<RouterParcelable> list) {
        if (listObjMap == null) {
            listObjMap = new HashMap<>();
        }
        listObjMap.put(getReadIndex(), list);
    }

    @Override
    public void readLists(List<List<?>> list) {
        if (listsMap == null) {
            listsMap = new HashMap<>();
        }
        listsMap.put(getReadIndex(), list);
    }

    private <T> void getListBasicFromMap(List<T> list) {
        if (listBasicMap == null) {
            return;
        }

        List<T> source = listBasicMap.get(getWriteIndex());
//        if (source == null || list == null || source.size() != list.size()) {
//            list = source;
//        }
        list = source;
    }

    @Override
    public void writeListByte(List<Byte> list) {
        getListBasicFromMap(list);
    }

    @Override
    public void writeListShort(List<Short> list) {
        getListBasicFromMap(list);
    }

    @Override
    public void writeListInt(List<Integer> list) {
        getListBasicFromMap(list);
    }

    @Override
    public void writeListLong(List<Long> list) {
        getListBasicFromMap(list);
    }

    @Override
    public void writeListFloat(List<Float> list) {
        getListBasicFromMap(list);
    }

    @Override
    public void writeListDouble(List<Double> list) {
        getListBasicFromMap(list);
    }

    @Override
    public void writeListChar(List<Character> list) {
        getListBasicFromMap(list);
    }

    @Override
    public void writeBool(List<Boolean> list) {
        getListBasicFromMap(list);
    }

    @Override
    public void writeListStr(List<String> list) {
        getListBasicFromMap(list);
    }

    @Override
    public void writeListObj(List<RouterParcelable> list) {
        if (listObjMap == null) {
            return;
        }
        List<RouterParcelable> sourceList = listObjMap.get(getWriteIndex());
        if (sourceList == null || list == null || sourceList.size() != list.size()) {
            return;
        }
        for (int i = 0; i < sourceList.size(); i++) {
            RouterParcelable source = sourceList.get(i);
            DataConvert convert = new DataConvert();
            source.routerParcelableRead(convert);
            RouterParcelable desData = list.get(i);
            desData.routerParcelableWrite(convert);
        }
    }

    @Override
    public void writeLists(List<List<?>> list) {

    }
}
