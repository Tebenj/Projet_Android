package com.benjamin.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {
    SharedPreferences history;
    ArrayList<String> hist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        history = getSharedPreferences("history", MODE_PRIVATE);
        // On obtient l'historique
        for (int i = 0;; i++) {
            final String string = history.getString(String.valueOf(i), "");
            if (!string.equals("")){
                hist.add(string);
            } else {
                break; // On sort de la boucle
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                hist
                );
        ListView listView = (ListView) findViewById(R.id.lv_hist);
        listView.setAdapter(arrayAdapter);
    }
}