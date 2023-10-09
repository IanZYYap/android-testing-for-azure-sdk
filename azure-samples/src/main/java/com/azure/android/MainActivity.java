package com.azure.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.azure.android.appconfiguration.HelloWorld;
import com.azure.android.appconfiguration.SecretReferenceConfigurationSettingSample;
import com.azure.android.appconfiguration.WatchFeature;
import com.azure.android.appconfiguration.ConditionalRequestAsync;

import com.azure.android.keyvault.certificates.HelloWorldKeyvaultCertificates;
import com.azure.android.keyvault.certificates.ListOperationsKeyvaultCertificates;
import com.azure.android.keyvault.certificates.ManagingDeletedCertificatesAsyncKeyvaultCertificates;
import com.azure.android.keyvault.keys.HelloWorldKeyvaultKeys;
import com.azure.android.keyvault.keys.KeyRotationAsyncKeyvaultKeys;

import com.azure.android.keyvault.secrets.HelloWorldKeyvaultSecrets;
import com.azure.android.keyvault.secrets.ListOperationsAsyncKeyvaultSecrets;
import com.azure.android.keyvault.secrets.ManagingDeletedSecretsKeyvaultSecrets;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // These are obtained by setting system environment variables
        // on the computer emulating the app
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(BuildConfig.AZURE_CLIENT_ID)
                .clientSecret(BuildConfig.AZURE_CLIENT_SECRET)
                .tenantId(BuildConfig.AZURE_TENANT_ID)
                .build();

        final String keyvaultEndpoint = "https://android-key-vault.vault.azure.net/";
        final String appconfigEndpoint = "https://android-app-configuration.azconfig.io";
        final String storageAccountName = "androidazsdkstorage";
        Thread thread = new Thread(() -> {
            try {
                //appconfig sample block
                HelloWorld.main(appconfigEndpoint, clientSecretCredential);
                WatchFeature.main(appconfigEndpoint, clientSecretCredential);
                //CreateSnapshot.main(appconfigEndpoint, clientSecretCredential);
                SecretReferenceConfigurationSettingSample.main(appconfigEndpoint, clientSecretCredential);

                try {
                    ConditionalRequestAsync.main(appconfigEndpoint, clientSecretCredential);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                //keyvault-keys sample block
                try {
                    HelloWorldKeyvaultKeys.main(keyvaultEndpoint, clientSecretCredential);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                KeyRotationAsyncKeyvaultKeys.main(keyvaultEndpoint, clientSecretCredential);

                /* commented out pending key-id being obtained to put in this
                try {
                    KeyWrapUnwrapOperationsKeyvaultKeys.main(keyvaultEndpoint, clientSecretCredential);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                */

                // keyvault-secrets sample block
                try {
                    HelloWorldKeyvaultSecrets.main(keyvaultEndpoint, clientSecretCredential);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    ListOperationsAsyncKeyvaultSecrets.main(keyvaultEndpoint, clientSecretCredential);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                try {
                    ManagingDeletedSecretsKeyvaultSecrets.main(keyvaultEndpoint, clientSecretCredential);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                //keyvault-certificates sample block

                try {
                    HelloWorldKeyvaultCertificates.main(keyvaultEndpoint, clientSecretCredential);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ListOperationsKeyvaultCertificates.main(keyvaultEndpoint, clientSecretCredential);
                try {
                    ManagingDeletedCertificatesAsyncKeyvaultCertificates.main(keyvaultEndpoint, clientSecretCredential);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // storage-blob sample block
                /* this has separate breaking issues in both API 26 and higher API levels
                try {
                    BasicExample.main(storageAccountName, clientSecretCredential);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                */
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }
}
