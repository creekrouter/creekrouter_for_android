package com.creek.module_c;

import com.creek.router.annotation.CreekMethod;

public interface CALLBACK {
    @CreekMethod(path = "call_back_get_word")
    void getWord(String word);
}
