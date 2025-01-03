plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("de.mannodermaus.android-junit5") version "1.11.2.0"
}

android {
    namespace = "tech.igrant.remindcat"
    compileSdk = 35

    defaultConfig {
        applicationId = "tech.igrant.remindcat"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests.all {
            it.systemProperty("buildDir", project.layout.buildDirectory.asFile.get().absolutePath)
        }
    }
}

dependencies {

    implementation(libs.kotlin.reflect)
    implementation(libs.gson)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Jetpack Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.activity.compose)


    implementation(libs.retrofit)

    implementation(libs.koin.android)

}