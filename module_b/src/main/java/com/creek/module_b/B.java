package com.creek.module_b;

import android.content.Context;
import android.widget.Toast;

import com.creek.router.annotation.CreekMethod;

public class B {

    @CreekMethod(path = "example_say_hi")
    public String getHi() {
        return "hi";
    }

    @CreekMethod(path = "example_say_hello_world")
    public static boolean sayHelloWorld(Context context, String content) {
        if (context == null || context == null || content.length() == 0) {
            return false;
        }
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        return true;
    }
}
