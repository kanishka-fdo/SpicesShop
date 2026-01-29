plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // ✅ Apply plugin WITHOUT version (version comes from root build.gradle.kts)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.example.spicesshop"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.spicesshop"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    // ✅ Compose compiler 1.5.10 requires Kotlin 1.9.22
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Coil (images)
    implementation("io.coil-kt:coil-compose:2.6.0")

    // DataStore (settings)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Google Maps SDK (optional if you add real map later)
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
}
