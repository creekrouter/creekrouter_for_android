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

package com.creek.router.processor.base;

import com.creek.router.exception.CompileException;
import com.creek.router.generate.java.FilerGen;


import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public abstract class BaseProcessor extends AbstractProcessor {

    protected Messager messager;
    protected Filer filer;
    protected FilerGen filerGen;

    @Override
    public final synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        this.messager = processingEnvironment.getMessager();
        this.filer = processingEnv.getFiler();
        this.filerGen = new FilerGen(this.filer);
        CompileException.messager = this.messager;
        init();
    }

    @Override
    public final SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public final Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationSet = getScanAnnotation();
        if (annotationSet == null) {
            return new HashSet<>();
        }
        return annotationSet;
    }

    @Override
    public final boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        process(roundEnvironment);
        return false;
    }

    public final void print(String str) {
        this.messager.printMessage(Diagnostic.Kind.NOTE, str);
    }

    public final void printError(String str) {
        this.messager.printMessage(Diagnostic.Kind.ERROR, str);
    }

    public final void printOther(String str) {
        this.messager.printMessage(Diagnostic.Kind.OTHER, str);
    }

    public void init() {

    }

    public Set<String> getScanAnnotation() {
        return null;
    }

    public void process(RoundEnvironment roundEnvironment) {

    }
}
