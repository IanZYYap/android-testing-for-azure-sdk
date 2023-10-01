package com.azure.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

//import com.azure.data.appconfiguration.implementation.ClientConstants;
import com.azure.android.appconfiguration.HelloWorld;
import com.azure.android.appconfiguration.SecretReferenceConfigurationSettingSample;
//import com.azure.data.appconfiguration.CreateSnapshot;
import com.azure.android.appconfiguration.WatchFeature;
import com.azure.android.appconfiguration.ConditionalRequestAsync;

import com.azure.android.eventhubs.ConsumeEvents;
import com.azure.android.eventhubs.EventProcessorClientSample;
import com.azure.android.eventhubs.PublishEventsWithAzureIdentity;
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
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import java.io.IOException;

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
                    HelloWorldKeyvaultCerificates.main(keyvaultEndpoint, clientSecretCredential);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ListOperationsKeyvaultCerificates.main(keyvaultEndpoint, clientSecretCredential);
                try {
                    ManagingDeletedCertificatesAsyncKeyvaultCerificates.main(keyvaultEndpoint, clientSecretCredential);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // storage-blob sample block
                try {
                    BasicExample.main(storageAccountName, clientSecretCredential);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Eventhubs sample block
                // "<<fully-qualified-namespace>>" will look similar to "{your-namespace}.servicebus.windows.net"
                // "<<event-hub-name>>" will be the name of the Event Hub instance you created inside the Event Hubs namespace.
                // TODO: Get necessary details for eventhubs and uncomment function calls.
                String[] eventargs = {"<<fully-qualified-namespace>>", "<<event-hub-name>>"};
                //Log.i("Eventhubs", "Starting ConsumeEvents");
                //ConsumeEvents.main(eventargs);
                //Log.i("Eventhubs", "Starting PublishEvents");
                //PublishEventsWithAzureIdentity.main(eventargs);
                //Log.i("Eventhubs", "Starting EventProcessorClientSample");
                //EventProcessorClientSample.main(eventargs);

                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }
}
