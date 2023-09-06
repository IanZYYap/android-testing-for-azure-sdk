import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    //id("de.mannodermaus.android-junit5") version "1.9.3.0"
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

    packagingOptions() {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")


    //For running existing tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //androidTestImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    //androidTestImplementation("de.mannodermaus.junit5:android-test-core:1.3.0")
    //androidTestRuntimeOnly("de.mannodermaus.junit5:android-test-runner:1.3.0")

    // For testing Alan's branch in azure-core
    // Strangely, the first two are already in the core pom.xml but still need to be called
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.7")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    // https://mvnrepository.com/artifact/org.reactivestreams/reactive-streams
    implementation("org.reactivestreams:reactive-streams:1.0.4")

    // TODO: Set up different builds to avoid constant manual dependency changes
    // For testing issue 1540, https://github.com/microsoftgraph/msgraph-sdk-java/issues/1540
    // https://mvnrepository.com/artifact/com.azure/azure-identity
    implementation("com.azure:azure-identity:1.10.0")
    //implementation(files("libs\\azure-identity-1.11.0-beta.1.jar"))
    // https://mvnrepository.com/artifact/com.azure/azure-core
    implementation("com.azure:azure-core:1.42.0")
    //implementation(files("libs\\azure-core-1.43.0-beta.1.jar"))

    //For testing issue 35756, https://github.com/Azure/azure-sdk-for-java/issues/35756
    // https://mvnrepository.com/artifact/com.azure/azure-ai-translation-text
    implementation("com.azure:azure-ai-translation-text:1.0.0-beta.1")
    // implementation(files("libs\\azure-ai-translation-text-1.0.0-beta.2.jar"))
    // https://mvnrepository.com/artifact/com.azure/azure-core-http-netty
    implementation("com.azure:azure-core-http-netty:1.14.0-beta.1")
    //implementation(files("libs\\azure-core-http-netty-1.14.0-beta.2.jar"))
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")

    //For testing issue 35719, https://github.com/Azure/azure-sdk-for-java/issues/35719
    implementation("com.azure:azure-ai-openai:1.0.0-beta.2")
    //implementation(files("libs\\azure-ai-openai-1.0.0-beta.5.jar"))

}