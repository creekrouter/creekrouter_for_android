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

package com.creek.router.generate.txt;

import java.io.File;
import java.io.IOException;

public class TxtCreator {
    public static void createFileIfNone(String txtPath) {
        File file = new File(txtPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
            }
        }
    }
    public static void createDir(String dirPath){
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
