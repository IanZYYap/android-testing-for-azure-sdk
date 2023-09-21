package com.azuresamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//import com.azure.data.appconfiguration.implementation.ClientConstants;
import com.azuresamples.appconfiguration.HelloWorld;
import com.azuresamples.appconfiguration.SecretReferenceConfigurationSettingSample;
//import com.azure.data.appconfiguration.CreateSnapshot;
import com.azuresamples.appconfiguration.WatchFeature;
import com.azuresamples.appconfiguration.ConditionalRequestAsync;

import com.azuresamples.keyvault.certificates.BackupAndRestoreOperationsKeyvaultCerificates;
import com.azuresamples.keyvault.certificates.HelloWorldKeyvaultCerificates;
import com.azuresamples.keyvault.certificates.ListOperationsKeyvaultCerificates;
import com.azuresamples.keyvault.certificates.ManagingDeletedCertificatesAsyncKeyvaultCerificates;
import com.azuresamples.keyvault.keys.HelloWorldKeyvaultKeys;
import com.azuresamples.keyvault.keys.KeyRotationAsyncKeyvaultKeys;
import com.azuresamples.keyvault.keys.BackupAndRestoreOperationsKeyvaultKeys;
//import com.azuresamples.keyvault.keys.KeyWrapUnwrapOperations;

import com.azuresamples.keyvault.keys.KeyWrapUnwrapOperationsKeyvaultKeys;
import com.azuresamples.keyvault.secrets.BackupAndRestoreOperationsKeyvaultSecrets;
import com.azuresamples.keyvault.secrets.HelloWorldKeyvaultSecrets;
import com.azuresamples.keyvault.secrets.ListOperationsAsyncKeyvaultSecrets;
import com.azuresamples.keyvault.secrets.ManagingDeletedSecretsKeyvaultSecrets;
import com.azuresamples.storage.BasicExample;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String[] keyvaultCredentials = {"endpoint", "client-id", "client secret", "tenant id"};
    private final String[] appconfigCredentials = {"Put appconfig credential string here"};
    private final String[] storageCredentials = {"Put storage endpoint here", "Put storage secret key here"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
