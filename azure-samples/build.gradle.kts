plugins {
    id("com.android.application")
}

android {
    namespace = "com.azuresamples"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.azuresamples"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packaging {
        resources {
            excludes += "/META-INF/*"
        }
    }
}


dependencies {
    implementation(project.dependencies.platform("com.azure:azure-sdk-bom:1.2.16"))
    // azure core
    implementation("com.azure:azure-core")
    implementation("com.azure:azure-json")
    implementation("com.azure:azure-core-http-netty")
    // azure_appconfig

    // azure_keyvault

    // azure_storage
    implementation("com.azure:azure-storage-blob")
    // azure_identity

    // android
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")

    // xml
    implementation("com.azure:azure-xml:1.0.0-beta.2")
    implementation ("stax:stax:1.2.0")

    // testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // jackson
    implementation(project.dependencies.platform("com.fasterxml.jackson:jackson-bom:2.13.5"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin") {
        version {
            strictly("2.13.5")
        }
    }
    implementation("com.fasterxml.jackson.core:jackson-core") {
        version{
            strictly("2.13.5")
        }
    }
    implementation("com.fasterxml.jackson.core:jackson-databind"){
        version{
            strictly("2.13.5")
        }
    }
    implementation("com.fasterxml.jackson.core:jackson-annotations"){
        version{
            strictly("2.13.5")
        }
    }
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml"){
        version{
            strictly("2.13.5")
        }
    }
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310"){
        version{
            strictly("2.13.5")
        }
    }
}