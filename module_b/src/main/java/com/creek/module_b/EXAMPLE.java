package com.creek.module_b;

import com.creek.router.annotation.CreekMethod;

public class EXAMPLE {

    @CreekMethod(path = "example_say_hi")
    public String sayHi() {
        return "hi,Creek Router!";
    }

    @CreekMethod(path = "example_say_a_b_c")
    public String sayABC(String word) {
        return word + " B C ";
    }
}