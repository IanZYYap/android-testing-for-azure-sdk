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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.OffsetDateTime;

/**
 * Sample demonstrates how to backup and restore secrets in the key vault.
 */
public class BackupAndRestoreOperationsKeyvaultSecrets {
    /**
     * Authenticates with the key vault and shows how to backup and restore secrets in the key vault.
     *
     * @throws IllegalArgumentException when invalid key vault endpoint is passed.
     * @throws InterruptedException when the thread is interrupted in sleep mode.
     * @throws IOException when writing backup to file is unsuccessful.
     */
    private static final String TAG = "BackupRestoreSecret";

    public static void main(String endpoint, ClientSecretCredential clientSecretCredential) throws IOException, InterruptedException, IllegalArgumentException {
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

        // Let's create secrets holding storage account credentials valid for 1 year. If the secret already exists in
        // the key vault, then a new version of the secret is created.
        client.setSecret(new KeyVaultSecret("StorageAccountPassword", "f4G34fMh8v-fdsgjsk2323=-asdsdfsdf")
            .setProperties(new SecretProperties()
                .setExpiresOn(OffsetDateTime.now().plusYears(1))));

        // Backups are good to have, if in case secrets get accidentally deleted by you.
        // For long term storage, it is ideal to write the backup to a file.
        String backupFilePath = "YOUR_BACKUP_FILE_PATH";
        byte[] secretBackup = client.backupSecret("StorageAccountPassword");

        writeBackupToFile(secretBackup, backupFilePath);

        // The storage account secret is no longer in use, so you delete it.
        SyncPoller<DeletedSecret, Void> deletedStorageSecretPoller =
            client.beginDeleteSecret("StorageAccountPassword");
        PollResponse<DeletedSecret>  pollResponse = deletedStorageSecretPoller.poll();
        DeletedSecret deletedStorageSecret = pollResponse.getValue();

        Log.i(TAG, String.format("Deleted Date %s", deletedStorageSecret.getDeletedOn().toString()));
        Log.i(TAG, String.format("Deleted Secret's Recovery Id %s", deletedStorageSecret.getRecoveryId()));

        // Secret is being deleted on server.
        deletedStorageSecretPoller.waitForCompletion();

        //To ensure the secret is deleted server-side.
        Thread.sleep(30000);

        // If the vault is soft-delete enabled, then you need to purge the secret as well for permanent deletion.
        client.purgeDeletedSecret("StorageAccountPassword");

        // To ensure the secret is purged server-side.
        Thread.sleep(15000);

        // After sometime, the secret is required again. We can use the backup value to restore it in the key vault.
        byte[] backupFromFile = Files.readAllBytes(new File(backupFilePath).toPath());
        KeyVaultSecret restoredSecret = client.restoreSecretBackup(backupFromFile);
    }

    private static void writeBackupToFile(byte[] bytes, String filePath) {
        try {
            File file = new File(filePath);

            if (file.exists()) {
                file.delete();
            }

            file.createNewFile();

            OutputStream os = new FileOutputStream(file);
            os.write(bytes);

            Log.i(TAG, "Successfully wrote backup to file.");

            // Close the file
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
