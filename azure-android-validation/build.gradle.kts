plugins {
    id("com.android.library")
}

android {
    namespace = "com.samples.azureandroidvalidation"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(files("libs\\azure-core-1.43.0-beta.1.jar"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Needed to test Alan's ReflectionUtils.
    // Strangely, the first two are already in the core pom.xml but still need to be called
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.7")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    // https://mvnrepository.com/artifact/org.reactivestreams/reactive-streams
    implementation("org.reactivestreams:reactive-streams:1.0.4")

    // https://mvnrepository.com/artifact/com.azure/azure-xml
    implementation("com.azure:azure-xml:1.0.0-beta.2")
    // https://mvnrepository.com/artifact/stax/stax
    implementation ("stax:stax:1.2.0")
    // https://mvnrepository.com/artifact/com.azure/azure-core
    //implementation("com.azure:azure-core:1.42.0")


}