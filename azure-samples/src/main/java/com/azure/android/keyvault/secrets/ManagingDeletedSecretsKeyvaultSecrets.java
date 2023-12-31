// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.android.keyvault.secrets;

import android.util.Log;

import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.DeletedSecret;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.azure.security.keyvault.secrets.models.SecretProperties;

import java.time.OffsetDateTime;

/**
 * Sample demonstrates how to list, recover and purge deleted secrets in a soft-delete enabled key vault.
 */
public class ManagingDeletedSecretsKeyvaultSecrets {
    /**
     * Authenticates with the key vault and shows how to list, recover and purge deleted secrets in a soft-delete
     * enabled key vault.
     *
     * @throws IllegalArgumentException when invalid key vault endpoint is passed.
     * @throws InterruptedException when the thread is interrupted in sleep mode.
     */
    private static final String TAG = "ManageDelSecrets";

    public static void main(String endpoint, ClientSecretCredential clientSecretCredential) throws IllegalArgumentException, InterruptedException {
        /* NOTE: To manage deleted secrets, your key vault needs to have soft-delete enabled. Soft-delete allows deleted
        secrets to be retained for a given retention period (90 days). During this period deleted secrets can be
        recovered and if a secret needs to be permanently deleted then it needs to be purged. */

        /* Instantiate a SecretClient that will be used to call the service. Notice that the client is using default
        Azure credentials. For more information on this and other types of credentials, see this document:
        https://docs.microsoft.com/java/api/overview/azure/identity-readme?view=azure-java-stable.

        To get started, you'll need a URL to an Azure Key Vault. See the README
        (https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/keyvault/azure-security-keyvault-secrets/README.md)
        for links and instructions. */

        SecretClient client = new SecretClientBuilder()
                .vaultUrl(endpoint)
                .credential(clientSecretCredential)
                .buildClient();

        // Let's create secrets holding storage and bank accounts credentials valid for 1 year. If the secret already
        // exists in the key vault, then a new version of the secret is created.
        client.setSecret(new KeyVaultSecret("StorageAccountPassword", "f4G34fMh8v-fdsgjsk2323=-asdsdfsdf")
            .setProperties(new SecretProperties()
                .setExpiresOn(OffsetDateTime.now().plusYears(1))));

        client.setSecret(new KeyVaultSecret("BankAccountPassword", "f4G34fMh8v")
            .setProperties(new SecretProperties()
                .setExpiresOn(OffsetDateTime.now().plusYears(1))));

        // The storage account was closed, need to delete its credentials from the key vault.
        SyncPoller<DeletedSecret, Void> deletedBankSecretPoller = client.beginDeleteSecret("BankAccountPassword");

        PollResponse<DeletedSecret> deletedBankSecretPollResponse = deletedBankSecretPoller.poll();

        Log.i(TAG, String.format("Deleted Date %s", deletedBankSecretPollResponse.getValue().getDeletedOn().toString()));
        Log.i(TAG, String.format("Deleted Secret's Recovery Id %s", deletedBankSecretPollResponse.getValue().getRecoveryId()));

        // The secret is being deleted on the server.
        deletedBankSecretPoller.waitForCompletion();

        // We accidentally deleted bank account secret. Let's recover it.
        // A deleted secret can only be recovered if the key vault is soft-delete enabled.
        SyncPoller<KeyVaultSecret, Void> recoverSecretPoller =
            client.beginRecoverDeletedSecret("BankAccountPassword");

        PollResponse<KeyVaultSecret> recoverSecretResponse = recoverSecretPoller.poll();

        Log.i(TAG, String.format("Recovered Key Name %s", recoverSecretResponse.getValue().getName()));
        Log.i(TAG, String.format("Recovered Key's Id %s", recoverSecretResponse.getValue().getId()));

        // The secret is being recovered on the server.
        recoverSecretPoller.waitForCompletion();

        // The bank account and storage accounts got closed.
        // Let's delete bank and storage accounts secrets.
        SyncPoller<DeletedSecret, Void> deletedBankPwdSecretPoller =
            client.beginDeleteSecret("BankAccountPassword");
        PollResponse<DeletedSecret> deletedBankPwdSecretPollResponse = deletedBankPwdSecretPoller.poll();

        Log.i(TAG, String.format("Deleted Date %s", deletedBankPwdSecretPollResponse.getValue().getDeletedOn().toString()));
        Log.i(TAG, String.format("Deleted Secret's Recovery Id %s",
            deletedBankPwdSecretPollResponse.getValue().getRecoveryId()));

        // The secret is being deleted on the server.
        deletedBankPwdSecretPoller.waitForCompletion();

        SyncPoller<DeletedSecret, Void> deletedStorageSecretPoller =
            client.beginDeleteSecret("StorageAccountPassword");
        PollResponse<DeletedSecret> deletedStorageSecretPollResponse = deletedStorageSecretPoller.poll();

        Log.i(TAG, String.format("Deleted Date  %s", deletedStorageSecretPollResponse.getValue().getDeletedOn().toString()));
        Log.i(TAG, String.format("Deleted Secret's Recovery Id %s", deletedStorageSecretPollResponse.getValue().getRecoveryId()));

        // The secret is being deleted on the server.
        deletedStorageSecretPoller.waitForCompletion();

        // You can list all the deleted and non-purged secrets, assuming key vault is soft-delete enabled.
        for (DeletedSecret delSecret : client.listDeletedSecrets()) {
            Log.i(TAG, String.format("Deleted secret's recovery Id %s", delSecret.getRecoveryId()));
        }

        // If the key vault is soft-delete enabled, then for permanent deletion deleted secrets need to be purged.
        client.purgeDeletedSecret("StorageAccountPassword");
        client.purgeDeletedSecret("BankAccountPassword");

        // To ensure the secret is purged server-side.
        Thread.sleep(15000);
    }
}
