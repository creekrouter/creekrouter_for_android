package com.creek.module_b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.creek.router.annotation.CreekClass;

@CreekClass(path = "module_b_ModuleActivity_B")
public class ModuleActivity_B extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Module b Activity");
        setContentView(R.layout.activity_module_b);
    }
}