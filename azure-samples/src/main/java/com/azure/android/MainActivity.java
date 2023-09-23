package com.azure.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//import com.azure.data.appconfiguration.implementation.ClientConstants;
import com.azure.android.appconfiguration.HelloWorld;
import com.azure.android.appconfiguration.SecretReferenceConfigurationSettingSample;
//import com.azure.data.appconfiguration.CreateSnapshot;
import com.azure.android.appconfiguration.WatchFeature;
import com.azure.android.appconfiguration.ConditionalRequestAsync;

import com.azure.android.keyvault.certificates.HelloWorldKeyvaultCerificates;
import com.azure.android.keyvault.certificates.ListOperationsKeyvaultCerificates;
import com.azure.android.keyvault.certificates.ManagingDeletedCertificatesAsyncKeyvaultCerificates;
import com.azure.android.keyvault.keys.HelloWorldKeyvaultKeys;
import com.azure.android.keyvault.keys.KeyRotationAsyncKeyvaultKeys;
//import com.azuresamples.keyvault.keys.KeyWrapUnwrapOperations;

import com.azure.android.keyvault.keys.KeyWrapUnwrapOperationsKeyvaultKeys;
import com.azure.android.keyvault.secrets.HelloWorldKeyvaultSecrets;
import com.azure.android.keyvault.secrets.ListOperationsAsyncKeyvaultSecrets;
import com.azure.android.keyvault.secrets.ManagingDeletedSecretsKeyvaultSecrets;
import com.azure.android.storage.BasicExample;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String[] keyvaultCredentials = {"endpoint", "client-id", "client secret", "tenant id"};
    private final String[] appconfigCredentials = {"Put appconfig credential string here"};
    private final String[] storageCredentials = {"Put storage endpoint here", "Put storage secret key here"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread thread = new Thread(() -> {
            try {
                //appconfig sample block
                HelloWorld.main(appconfigCredentials);
                WatchFeature.main(appconfigCredentials);
                //CreateSnapshot.main(appConfigCredentials);
                SecretReferenceConfigurationSettingSample.main(appconfigCredentials);

                try {
                    ConditionalRequestAsync.main(appconfigCredentials);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //keyvault-keys sample block
                try {
                    HelloWorldKeyvaultKeys.main(keyvaultCredentials);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                KeyRotationAsyncKeyvaultKeys.main(keyvaultCredentials);


                try {
                    KeyWrapUnwrapOperationsKeyvaultKeys.main(keyvaultCredentials);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // keyvault-secrets sample block
                try {
                    HelloWorldKeyvaultSecrets.main(keyvaultCredentials);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    ListOperationsAsyncKeyvaultSecrets.main(keyvaultCredentials);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    ManagingDeletedSecretsKeyvaultSecrets.main(keyvaultCredentials);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                //keyvault-certificates sample block

                try {
                    HelloWorldKeyvaultCerificates.main(keyvaultCredentials);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ListOperationsKeyvaultCerificates.main(keyvaultCredentials);
                try {
                    ManagingDeletedCertificatesAsyncKeyvaultCerificates.main(keyvaultCredentials);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                // storage-blob sample block
                try {
                    BasicExample.main(storageCredentials);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }
}
