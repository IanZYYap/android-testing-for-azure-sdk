# Android Testing for Azure SDK for Java
The purpose of this project is to improve Android support in the Azure SDK, by determining a baseline level of compatibility at API levels 26 and above. It is currently in-progress, with many service libraries yet to be investigated.
The project currently contains:
- ported service samples that are set up to run within an Android environment. 
- instrumented tests to address specific issues identified by users or the Animal Sniffer plugin.

## Sampled Libraries
The following service libraries have been sampled in Android, and are likely to be compatible:
- `azure-data-appconfiguration`
- `azure-security-keyvault-keys`
- `azure-security-keyvault-secrets`
- `azure-security-keyvault-certificates`

## Known Issues
The following service libraries have known issues with Android:
- `azure-ai-translation-text`
- `azure-ai-openai`
- `azure-identity`
- `azure-core-http-netty`

## Testing Efforts
A number of Core libraries have known issues with Android:
- `azure-xml` requires an external [StAX dependency](https://mvnrepository.com/artifact/stax/stax) as Android is missing the `javax.xml.stream package`.
- `azure-core`'s `ReflectionSerializable` class also requires an external StAX dependency.