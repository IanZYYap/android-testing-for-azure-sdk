// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.android.keyvault.certificates;

import android.util.Log;

import com.azure.core.util.polling.LongRunningOperationStatus;
import com.azure.core.util.polling.SyncPoller;
import com.azure.identity.ClientSecretCredential;
import com.azure.security.keyvault.certificates.CertificateClient;
import com.azure.security.keyvault.certificates.CertificateClientBuilder;
import com.azure.security.keyvault.certificates.models.CertificateContact;
import com.azure.security.keyvault.certificates.models.CertificateIssuer;
import com.azure.security.keyvault.certificates.models.CertificateOperation;
import com.azure.security.keyvault.certificates.models.CertificatePolicy;
import com.azure.security.keyvault.certificates.models.CertificateProperties;
import com.azure.security.keyvault.certificates.models.IssuerProperties;
import com.azure.security.keyvault.certificates.models.KeyVaultCertificate;
import com.azure.security.keyvault.certificates.models.KeyVaultCertificateWithPolicy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Sample demonstrates how to perform list operation on certificates, certificate issuers and certificate contacts  in the key vault.
 */
public class ListOperationsKeyvaultCertificates {
    private static final String TAG = "CertListOps";

    /**
     * Authenticates with the key vault and shows how to list certificates, certificate issuers and contacts in the key vault.
     *
     * @throws IllegalArgumentException when invalid key vault endpoint is passed.
     */
    public static void main(String endpoint, ClientSecretCredential clientSecretCredential) throws IllegalArgumentException {
        /* Instantiate a CertificateClient that will be used to call the service. Notice that the client is using
        default Azure credentials. For more information on this and other types of credentials, see this document:
        https://docs.microsoft.com/java/api/overview/azure/identity-readme?view=azure-java-stable.

        To get started, you'll need a URL to an Azure Key Vault. See the README
        (https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/keyvault/azure-security-keyvault-certificates/README.md)
        for links and instructions. */

        Log.i(TAG, "tests");

        CertificateClient certificateClient = new CertificateClientBuilder()
                .vaultUrl(endpoint)
                .credential(clientSecretCredential)
                .buildClient();

        // Let's create a self-signed certificate valid for 1 year. If the certificate already exists in the key vault,
        // then a new version of the certificate is created.
        CertificatePolicy policy = new CertificatePolicy("Self", "CN=SelfSignedJavaPkcs12");
        Map<String, String> tags = new HashMap<>();
        tags.put("foo", "bar");

        // check if certificate name available



        SyncPoller<CertificateOperation, KeyVaultCertificateWithPolicy> certificatePoller =
            certificateClient.beginCreateCertificate("certName", policy, true, tags);
        certificatePoller.waitUntil(LongRunningOperationStatus.SUCCESSFULLY_COMPLETED);

        KeyVaultCertificate cert = certificatePoller.getFinalResult();

        // Let's create a certificate issuer.
        CertificateIssuer issuer = new CertificateIssuer("myIssuer", "Test");
        CertificateIssuer myIssuer = certificateClient.createIssuer(issuer);

        Log.i(TAG, String.format("Issuer created with name %s and provider %s", myIssuer.getName(), myIssuer.getProvider()));

        // Let's create a certificate signed by our issuer.
        certificateClient.beginCreateCertificate("myCertificate",
            new CertificatePolicy("myIssuer", "CN=SignedJavaPkcs12"), true, tags)
            .waitUntil(LongRunningOperationStatus.SUCCESSFULLY_COMPLETED);


        // Let's list all the certificates in the key vault.
        for (CertificateProperties certificate : certificateClient.listPropertiesOfCertificates()) {
            KeyVaultCertificate certificateWithAllProperties =
                certificateClient.getCertificateVersion(certificate.getName(), certificate.getVersion());

            Log.i(TAG, String.format("Received certificate with name %s and secret id %s",
                certificateWithAllProperties.getProperties().getName(), certificateWithAllProperties.getSecretId()));
        }

        // Let's list all certificate versions of the certificate.
        for (CertificateProperties certificate : certificateClient.listPropertiesOfCertificateVersions("myCertificate")) {
            KeyVaultCertificate certificateWithAllProperties =
                certificateClient.getCertificateVersion(certificate.getName(), certificate.getVersion());

            Log.i(TAG, String.format("Received certificate with name %s and version %s",
                certificateWithAllProperties.getProperties().getName(),
                certificateWithAllProperties.getProperties().getVersion()));
        }

        // Let's list all certificate issuers in the key vault.
        for (IssuerProperties certIssuer : certificateClient.listPropertiesOfIssuers()) {
            CertificateIssuer retrievedIssuer = certificateClient.getIssuer(certIssuer.getName());

            Log.i(TAG, String.format("Received issuer with name %s and provider %s", retrievedIssuer.getName(),
                retrievedIssuer.getProvider()));
        }

        // Let's set certificate contacts on the key vault.
        CertificateContact contactToAdd = new CertificateContact().setName("user").setEmail("useremail@example.com");
        for (CertificateContact contact : certificateClient.setContacts(Collections.singletonList(contactToAdd))) {
            Log.i(TAG, String.format("Added contact with name %s and email %s to key vault", contact.getName(),
                contact.getEmail()));
        }

        // Let's list all certificate contacts in the key vault.
        for (CertificateContact contact : certificateClient.listContacts()) {
            Log.i(TAG, String.format("Retrieved contact with name %s and email %s from the key vault", contact.getName(),
                contact.getEmail()));
        }

        // Let's delete all certificate contacts in the key vault.
        for (CertificateContact contact : certificateClient.deleteContacts()) {
            Log.i(TAG, String.format("Deleted contact with name %s and email %s from key vault", contact.getName(),
                contact.getEmail()));
        }
    }
}
