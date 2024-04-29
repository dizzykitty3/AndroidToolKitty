plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.google.hilt)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.jetbrains.kotlin)
}

android {
    namespace = "me.dizzykitty3.androidtoolkitty"
    compileSdk = 34

    defaultConfig {
        applicationId = "me.dizzykitty3.androidtoolkitty"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    defaultConfig {
        resValue("string", "app_name", "ToolKitty")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = " DEV"
            resValue("string", "app_name", "ATK dev")
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        // https://developer.android.com/jetpack/androidx/releases/compose-compiler
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.squareup.leakcanary.android) // To detect memory leak

    implementation(libs.android.gms.play.services.maps)
    implementation(libs.android.material) // Theme.Material3.DynamicColors.DayNight
    implementation(libs.androidx.activity.compose) // ComponentActivity, setContent, enableEdgeToEdge
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.compose.ui.tooling.preview) // @Preview
    implementation(libs.androidx.core.ktx) // WindowCompat, ActivityCompat
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose) // NavHostController
    implementation(libs.androidx.room.ktx) // To use Coroutine features
    implementation(libs.androidx.room.runtime)
    implementation(libs.google.gson)
    implementation(libs.google.hilt) // Dependency injection
    implementation(libs.kotlinx.coroutines) // Asynchronous tasks

    ksp(libs.androidx.room.compiler)
    ksp(libs.google.hilt.compiler)
}