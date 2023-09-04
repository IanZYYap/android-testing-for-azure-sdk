package com.azuresamples;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final String[] keyvaultCredentials = {"Put keyvault credentials here"};
    private final String[] appconfigCredentials = {"Enter appconfig credentials here"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}