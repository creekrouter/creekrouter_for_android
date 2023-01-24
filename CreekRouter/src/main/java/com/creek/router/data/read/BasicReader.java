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

package com.creek.router.data.read;

interface BasicReader {
    void read(byte val_byte);

    void read(short val_short);

    void read(int val_int);

    void read(long val_long);

    void read(float val_float);

    void read(double val_double);

    void read(char val_char);

    void read(boolean val_bool);

    void read(String val_str);
}
