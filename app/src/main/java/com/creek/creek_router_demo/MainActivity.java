package com.creek.creek_router_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.creek.router.CreekRouter;

public class MainActivity extends AppCompatActivity {

    Button btn_a,btn_b,btn_c,btn_d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_a = findViewById(R.id.module_a);
        btn_b = findViewById(R.id.module_b);
        btn_c = findViewById(R.id.module_c);
        btn_d = findViewById(R.id.module_d);


        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> clazz = CreekRouter.getClazz("module_a_ModuleActivity_A");
                Intent intent = new Intent(MainActivity.this, clazz);
                startActivity(intent);
            }
        });

        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> clazz = CreekRouter.getClazz("module_b_ModuleActivity_B");
                Intent intent = new Intent(MainActivity.this, clazz);
                startActivity(intent);
            }
        });

        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> clazz = CreekRouter.getClazz("module_c_ModuleActivity_C");
                Intent intent = new Intent(MainActivity.this, clazz);
                startActivity(intent);
            }
        });

        btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> clazz = CreekRouter.getClazz("module_d_ModuleActivity_D");
                Intent intent = new Intent(MainActivity.this, clazz);
                startActivity(intent);
            }
        });
    }

}