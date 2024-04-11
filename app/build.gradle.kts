plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.room)
}

@Suppress("SpellCheckingInspection")
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

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
        }

        release {
            isMinifyEnabled = true
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
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
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
    implementation(libs.material) // Theme.Material3.DynamicColors.DayNight
    implementation(libs.play.services.maps)
    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))
    implementation(libs.activity.compose) // ComponentActivity, setContent, enableEdgeToEdge
    implementation(libs.material3)
    implementation(libs.material.icons.extended)
    implementation(libs.navigation.compose) // NavHostController
    implementation(libs.core.ktx) // WindowCompat, ActivityCompat
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.datastore.preferences)
    implementation(libs.gson)
}