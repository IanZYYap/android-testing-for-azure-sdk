package com.azuresamples;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.azuresamples.storage.BasicExample;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String[] keyvaultCredentials = {"Put keyvault credentials here"};
    private final String[] appconfigCredentials = {"Enter appconfig credentials here"};
    private final String[] storageCredentials = {"account name here", "Key here"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            BasicExample.main(storageCredentials);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}