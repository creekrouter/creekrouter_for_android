package com.creek.module_c;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.creek.router.CreekRouter;
import com.creek.router.annotation.CreekClass;

@CreekClass(path = "module_c_ModuleActivity_C")
public class ModuleActivity_C extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Module c Activity");
        setContentView(R.layout.activity_module_c);

        findViewById(R.id.button_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.244.243/lv/work/MyMail/app/build/outputs/apk/debug/app-debug.apk";
//                String url = "http://192.168.43.203/lv/work/MyMail/app/build/outputs/apk/debug/app-debug.apk";

//                String url = "http://192.168.43.203/lv/work/Example/app/build/outputs/apk/debug/app-debug.apk";
                CreekRouter.methodRun("demo_help_download", ModuleActivity_C.this, url);
            }
        });

        findViewById(R.id.button_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreekRouter.methodRun("demo_help_load", ModuleActivity_C.this, "com.creek.router.init.Plugin_Initializer_Main");
            }
        });

        findViewById(R.id.button_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String words = (String) CreekRouter.methodRun("plugin_demo");
                Toast.makeText(ModuleActivity_C.this, words, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button_d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreekRouter.methodRun("demo_help_clear", ModuleActivity_C.this);
            }
        });
    }
}