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

package com.creek.router.generate.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TxtReader {
    public static boolean isFileExist(String filePath) {
        File filename = new File(filePath);
        return filename.exists();
    }

    public static List<String> readSource(String txtPath) {
        File filename = new File(txtPath);
        if (!filename.exists()) {
            return null;
        }
        List<String> sourceList = new ArrayList<>();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = br.readLine();
            while (line != null) {
                sourceList.add(line);
                line = br.readLine();
            }
            br.close();
            reader.close();
        } catch (IOException e) {
            return null;
        }
        return sourceList;
    }

    public static String readTxt(String txtPath) {
        File filename = new File(txtPath);
        if (!filename.exists()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            while (line != null) {
                String tmp = line.trim();
                if (tmp.length() > 0) {
                    sb.append(tmp).append("\n");
                }
                line = br.readLine();
            }
            br.close();
            reader.close();
        } catch (IOException e) {

        }
        return sb.toString();
    }

    public static Set<String> readTrimLine(String txtPath) {
        File filename = new File(txtPath);
        Set<String> set = new HashSet<>();
        if (!filename.exists()) {
            return set;
        }
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            while (line != null) {
                String tmp = line.trim();
                if (tmp.length() > 0) {
                    set.add(tmp);
                }
                line = br.readLine();
            }
            br.close();
            reader.close();
        } catch (IOException e) {

        }
        return set;
    }

}
