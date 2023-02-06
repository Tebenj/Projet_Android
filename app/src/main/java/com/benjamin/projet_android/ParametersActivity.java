package com.benjamin.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class ParametersActivity extends AppCompatActivity {
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);

        settings = getSharedPreferences("settings", MODE_PRIVATE);
        load();
    }

    // On enregistre les changements
    public void saveSettings(View view) {
        Switch ecoMode = (Switch) findViewById(R.id.sw_eco_mode);
        EditText key = (EditText) findViewById(R.id.et_key);

        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("ecoMode", ecoMode.isChecked());
        editor.putString("key", key.getText().toString());
        editor.commit();

        // on retourne à l'activité principale
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    // On récupère les données
    private void load() {
        Switch ecoMode = (Switch) findViewById(R.id.sw_eco_mode);
        EditText key = (EditText) findViewById(R.id.et_key);

        ecoMode.setChecked(settings.getBoolean("ecoMode", false));
        key.setText(settings.getString("key", "f16099df-fc96-8c90-3beb-3c0eeda65bf8:fx"));
    }
}