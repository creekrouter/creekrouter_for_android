package com.creek.module_a;

import com.creek.router.annotation.CreekMethod;

public interface Example {
    @CreekMethod(path = "example_say_hi")
    String sayHi();

    @CreekMethod(path = "example_say_hello_world")
    void sayHelloWorld(String str, CallBack callBack);
}
