package com.creek.module_a;

import com.creek.router.annotation.CreekMethod;

public interface Example {
    @CreekMethod(path = "example_say_hi")
    String sayHi();
}
