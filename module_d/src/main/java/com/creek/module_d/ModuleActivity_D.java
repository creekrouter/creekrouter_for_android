package com.creek.module_d;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.creek.router.CreekRouter;
import com.creek.router.annotation.CreekClass;

@CreekClass(path = "module_d_ModuleActivity_D")
public class ModuleActivity_D extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Module d Activity");
        setContentView(R.layout.activity_module_d);

        findViewById(R.id.button_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> clazz = CreekRouter.getClazz("plugin_helper_PluginActivity");
                Intent intent = new Intent(ModuleActivity_D.this, clazz);
                intent.putExtra("class_annotate","plugin_mail_SettingActivity");
                startActivity(intent);
            }
        });
    }
}