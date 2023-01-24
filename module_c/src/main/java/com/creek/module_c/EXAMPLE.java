package com.creek.module_c;

import com.creek.router.annotation.CreekMethod;

public class EXAMPLE {

    @CreekMethod(path = "example_say_hello_world")
    public void sayHelloWorld(String str, CALLBACK callBack){
        String content = str + " World !";
        callBack.getWord(content);
    }
}
