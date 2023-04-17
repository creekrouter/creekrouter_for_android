package com.creek.module_a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.creek.router.CreekRouter;
import com.creek.router.annotation.CreekClass;

@CreekClass(path = "module_a_ModuleActivity_A")
public class ModuleActivity_A extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Module a Activity");
        setContentView(R.layout.activity_moudle_activity);


        findViewById(R.id.btn_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Example example = CreekRouter.create(Example.class);
                String word = example.sayHi();
                Toast.makeText(ModuleActivity_A.this, word, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = CreekRouter.methodRun("example_say_hello_world",ModuleActivity_A.this,"Hello World!");
            }
        });
    }
}