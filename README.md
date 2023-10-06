# Android Testing for Azure SDK for Java
The purpose of this project is to improve Android support for the Azure SDK, by determining a baseline level of compatibility at API levels 26 and above. It is currently in-progress, with many service libraries yet to be investigated.
Currently, Azure SDK services are not guaranteed to work in Android, though limited sampling at API levels >= 26 has shown that some services should be relatively compatible with Android.
The project structure currently contains:
- Ported service samples in `azure-samples` that are set up to run within an Android environment. 
- Instrumented tests to address specific issues that have been identified.

## Sampled Libraries
The following libraries have been sampled in Android, and are likely to be compatible:
- `azure-data-appconfiguration`
- `azure-security-keyvault-keys`
- `azure-security-keyvault-secrets`
- `azure-security-keyvault-certificates`

## Known Issues
The following libraries have known issues with Android:
- `azure-ai-translation-text` - undergoing resolution
- `azure-ai-openai` - undergoing resolution
- `azure-identity` - undergoing resolution
- `azure-core-http-netty`
- `azure-storage-blob`
A couple of Core libraries have minor known issues with Android:
- `azure-xml` requires an external [StAX dependency](https://mvnrepository.com/artifact/stax/stax) as Android is missing the `javax.xml.stream` package.
- `azure-core`'s `ReflectionSerializable` class also requires an external StAX dependency.

## Reporting and troubleshooting errors when using the SDK
If you encounter an error caused by the SDK that occurs in Android only, it is best to make an issue in the Azure SDK for Java repository, beginning the issue name with [ANDROID] to help distinguish it.
The following errors are a few examples that may occur when using the SDK in Android:
- `ExceptionInInitializerError`: An exception has occurred in the initializer for a class, and is most likely caused by one of the following two errors.
- `NoClassDefFoundError`: A package may be missing, as the Android JDKs have differences to the standard JDKs.
  - The error message will identify which package needs to be added as a dependency in your Gradle build.
- `NoSuchMethodError`: The SDK may be using a method that is not included in the current dependencies.
  - Updating the dependencies could resolve this issue. If an Azure SDK library is calling older dependency version transitively, you may need to import the latest version in Gradle and make the build choose that version over the version called by the Azure SDK library.
  - If you refer to documentation and the method is not included in any version of the dependencies available for Android, this could be due to the differences in JDKs. You may need to create an issue in the Azure SDK for Java repository so that this could potentially be resolved.
  - You could also try to run the code in emulators at multiple API levels from 26 and above, as newer API levels may introduce changes that cause incompatibilities. This could help to identify the root cause and assist with resolution.
If you are able to identify possible solutions to errors, i.e using alternate methods that are mutually compatible, then recording them in an issue and/or making a Pull Request to propose the fix could greatly expedite the issue resolution process.