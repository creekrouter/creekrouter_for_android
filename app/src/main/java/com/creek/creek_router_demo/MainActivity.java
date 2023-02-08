package com.creek.creek_router_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.creek.router.annotation.CreekClass;
import com.creek.router.annotation.CreekMethod;

@CreekClass(path = "app_main_activity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @CreekMethod(path = "hello_world")
    public void test(Context context) {

    }

}