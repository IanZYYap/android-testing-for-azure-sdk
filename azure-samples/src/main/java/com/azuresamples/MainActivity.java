package com.azuresamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.azure.data.appconfiguration.implementation.ClientConstants;
import com.azuresamples.appconfiguration.HelloWorld;
import com.azuresamples.appconfiguration.SecretReferenceConfigurationSettingSample;
//import com.azure.data.appconfiguration.CreateSnapshot;
import com.azuresamples.appconfiguration.WatchFeature;
import com.azuresamples.appconfiguration.ConditionalRequestAsync;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Proving that the app is interacting with appconfiguration
        String randomStringFromAppConfig = ClientConstants.APP_CONFIG_TRACING_NAMESPACE_VALUE;
        String[] connectionString = {"[Insert Connection String Here]"};

        // Sample main method calls
        // Comment out any call that you don't want

        HelloWorld.main(connectionString);
        WatchFeature.main(connectionString);
        //CreateSnapshot.main(connectionString);
        SecretReferenceConfigurationSettingSample.main(connectionString);

        try {
            ConditionalRequestAsync.main(connectionString);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        TextView text = findViewById(R.id.mainText);
        text.setText(randomStringFromAppConfig);
        Log.i("Tests", randomStringFromAppConfig);
    }
}