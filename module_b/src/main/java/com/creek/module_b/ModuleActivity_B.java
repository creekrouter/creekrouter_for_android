package com.creek.module_b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.creek.router.CreekRouter;
import com.creek.router.annotation.CreekClass;

@CreekClass(path = "module_b_ModuleActivity_B")
public class ModuleActivity_B extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Module b Activity");
        setContentView(R.layout.activity_module_b);


        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://81.70.151.252/download/RemoteJar.jar";
                CreekRouter.methodRun("demo_help_download", ModuleActivity_B.this, url);
            }
        });

        findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreekRouter.methodRun("demo_help_load", ModuleActivity_B.this, "com.creek.genCode.RoutingTable_RemoteJar");
            }
        });

        findViewById(R.id.button_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String words = (String) CreekRouter.methodRun("remote_jar_method");
                Toast.makeText(ModuleActivity_B.this, words, Toast.LENGTH_SHORT).show();

            }
        });

        findViewById(R.id.button_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreekRouter.methodRun("demo_help_clear", ModuleActivity_B.this);
            }
        });
    }
}