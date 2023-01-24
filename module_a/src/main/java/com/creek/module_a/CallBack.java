package com.creek.module_a;

import com.creek.router.annotation.CreekMethod;

public interface CallBack {

    @CreekMethod(path = "call_back_get_word")
    void getWord(String word);
}
