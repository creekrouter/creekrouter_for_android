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

package com.creek.router.generate.java;

import com.creek.router.configure.Config;

import java.io.IOException;
import java.io.Writer;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

public class FilerGen {
    private String pkgName = Config.PACKAGE + ".";
    private Filer filer;

    public FilerGen(Filer filer) {
        this.filer = filer;
    }

    public java.net.URI genJavaClass(String clazzStr, String className) {
        Writer writer = null;
        java.net.URI uri = null;
        try {
            JavaFileObject sourceFile = filer.createSourceFile(pkgName + className);
            writer = sourceFile.openWriter();
            String finalClazzStr = Config.ApacheLicense + Config.AuthorInfo + clazzStr;
            writer.write(finalClazzStr);
//            javaPath = sourceFile.toUri().getPath().toString();
            uri = sourceFile.toUri();
        } catch (Exception e) {

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uri;
    }
}
