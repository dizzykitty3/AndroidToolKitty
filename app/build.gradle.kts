plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler) // https://developer.android.com/develop/ui/compose/compiler
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "me.dizzykitty3.androidtoolkitty"
    compileSdk = libs.versions.compileSDK.get().toInt()

    buildFeatures {
        buildConfig = true
        compose = true
    }

    defaultConfig {
        applicationId = "me.dizzykitty3.androidtoolkitty"
        minSdk = libs.versions.minSDK.get().toInt()
        targetSdk = libs.versions.targetSDK.get().toInt()
        versionCode = 703
        versionName = "1.0.703"

        resValue("string", "app_name", "ToolKitty")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".dev"
            versionNameSuffix = ".dev"
            resValue("string", "app_name", "AToolKitty.Dev")
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

    applicationVariants.all {
        val outputFileName = "android-toolkitty-${this.versionName}.apk"
        outputs.all {
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl)
                .outputFileName = outputFileName
        }
    }

    composeCompiler {
        enableStrongSkippingMode = true
        reportsDestination = layout.buildDirectory.dir("compose_compiler")
//        stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            // https://github.com/Kotlin/kotlinx.coroutines#avoiding-including-the-debug-infrastructure-in-the-resulting-apk
            excludes += "DebugProbesKt.bin"
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
    debugImplementation(libs.square.leakcanary) // To detect memory leak

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
    implementation(libs.google.hilt.android) // Dependency injection
    implementation(libs.kotlinx.coroutines.android) // Asynchronous tasks
    implementation(libs.kotlinx.serialization) // json
    implementation(libs.timber) // logging

    ksp(libs.google.hilt.compiler)
    ksp(libs.androidx.room.compiler)
}