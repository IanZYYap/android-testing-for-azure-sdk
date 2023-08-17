plugins {
    id("com.android.application")
}

android {
    namespace = "com.samples.androidcompat"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.samples.androidcompat"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // https://mvnrepository.com/artifact/com.azure/azure-xml
    implementation("com.azure:azure-xml:1.0.0-beta.2")

    // This dependency is to provide javax.xml.stream which Android lacks
    // https://mvnrepository.com/artifact/stax/stax
    implementation("stax:stax:1.2.0")
}