package com.hi.demohelper.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.hi.demohelper.R;

import java.util.List;

public class PopWindow {
    public static void popListWindow(Context context, List<String> fileList, OnItemClickListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null, false);
        final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
        ListView listView = view.findViewById(R.id.list_view);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, fileList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout((context.getResources().getDisplayMetrics().widthPixels / 4 * 3), LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}
