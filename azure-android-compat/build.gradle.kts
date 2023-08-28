plugins {
    id("com.android.application")
    id("de.mannodermaus.android-junit5") version "1.9.3.0"
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

    //For running existing tests
    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    androidTestImplementation("de.mannodermaus.junit5:android-test-core:1.3.0")
    androidTestRuntimeOnly("de.mannodermaus.junit5:android-test-runner:1.3.0")

    // https://mvnrepository.com/artifact/com.azure/azure-xml
    implementation("com.azure:azure-xml:1.0.0-beta.2")

    // StAX provides javax.xml.stream which Android lacks. However, it lacks the newFactory
    // function from XMLInputFactory and XMLOutputFactory, which is called by DefaultXMLReader and
    // DefaultXMLWriter in azure-xml. This artifact may be overly general, but more specific
    // artifacts resulted in FactoryConfigurationErrors being thrown on newInstance(), due to
    // com.bea.xml.stream.* being missing.
    // https://mvnrepository.com/artifact/stax/stax
    // implementation ("stax:stax:1.2.0")
}