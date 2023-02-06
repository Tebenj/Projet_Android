package com.benjamin.projet_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.androidnetworking.interfaces.StringRequestListener;
import com.benjamin.projet_android.Language;

public class MainActivity extends AppCompatActivity {
    private EditText source;
    private EditText destination;
    private Spinner spinner;
    private boolean ecoMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(this);
        loadLanguages();

        source = (EditText) findViewById(R.id.et_source);
        destination = (EditText) findViewById(R.id.et_destination);
        spinner = (Spinner) findViewById(R.id.sp_languages);

        source.setHorizontallyScrolling(false);
        destination.setShowSoftInputOnFocus(false);

        source.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!ecoMode) {
                    translate(String.valueOf(source.getText()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        source.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    translate(String.valueOf(source.getText()));
                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        getUsage();
    }

    private void getUsage() {
        AndroidNetworking.get("https://api-free.deepl.com/v2/usage")
                .addHeaders("Authorization", "DeepL-Auth-Key f16099df-fc96-8c90-3beb-3c0eeda65bf8:fx")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            TextView usage = (TextView) findViewById(R.id.txt_usage);
                            usage.setText("Consommation\n" + response.getString("character_count") + " / " + response.getString("character_limit"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void loadLanguages() {
        // On conserve le contexte de l'activité
        Context that = this;

        AndroidNetworking.get("https://api-free.deepl.com/v2/languages")
                .addHeaders("Authorization", "DeepL-Auth-Key f16099df-fc96-8c90-3beb-3c0eeda65bf8:fx")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray languages) {
                        try {
                            // On récupère les langues
                            ArrayList<Language> languageList = new ArrayList<>();

                            for (int i = 0; i < languages.length(); i++) {
                                final JSONObject language = languages.getJSONObject(i);
                                languageList.add(new Language(
                                        language.getString("language"),
                                        language.getString("name")));
                            }

                            // On affiche les langues dans le spinner
                            ArrayAdapter<Language> adapter = new ArrayAdapter<>(that, android.R.layout.simple_spinner_dropdown_item, languageList);
                            spinner.setAdapter(adapter);

                            // On traduit à nouveau à chaque fois qu'on change de langue
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    translate(String.valueOf(source.getText()));
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError);
                    }
                });
    }

    public void translate(String text) {
        Language language = (Language) spinner.getSelectedItem();
        AndroidNetworking.post("https://api-free.deepl.com/v2/translate")
                .addHeaders("Authorization", "DeepL-Auth-Key f16099df-fc96-8c90-3beb-3c0eeda65bf8:fx")
                .addBodyParameter("text", text)
                .addBodyParameter("target_lang", language.getId())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // On récupère la traduction
                            JSONArray translations = response.getJSONArray("translations");
                            JSONObject translation = translations.getJSONObject(0);

                            // On affiche le texte traduit
                            destination.setText(translation.getString("text"));

                            // On affiche la langue détectée
                            TextView textViewSource = (TextView) findViewById(R.id.txt_source);
                            ArrayList<Language> languages = new ArrayList<>();
                            textViewSource.setText(translation.getString("detected_source_language"));

                            // On affiche la consommation
                            getUsage();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError);
                    }
                });
    }

    public void Historique(View view) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    public void Parametre(View view){
        Intent intent = new Intent(MainActivity.this, ParametersActivity.class);
        startActivity(intent);
    }
}

