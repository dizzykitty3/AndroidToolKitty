plugins {
    alias(libs.plugins.about.libraries)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
//    alias(libs.plugins.room)
}

android {
    namespace = "me.dizzykitty3.androidtoolkitty"
    compileSdk = 35
    buildFeatures {
        buildConfig = true
        compose = true
    }
    defaultConfig {
        applicationId = "me.dizzykitty3.androidtoolkitty"
        minSdk = 21
        targetSdk = compileSdk
        versionCode = 938
        versionName = "1.0.${versionCode}"

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
            resValue("string", "app_name", "ToolKitty Dev")
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
        val outputFileName = "android-toolkitty-${versionName}.apk"
        outputs.all {
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl)
                .outputFileName = outputFileName
        }
    }
    composeCompiler {
        enableStrongSkippingMode = true
        reportsDestination = layout.buildDirectory.dir("compose_compiler")
        // https://developer.android.com/develop/ui/compose/compiler#configuration-options
//        stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
    }
    compileOptions {
        // https://developer.android.com/studio/write/java8-support#library-desugaring
        isCoreLibraryDesugaringEnabled = true // Java 8+ API desugaring support
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        allWarningsAsErrors = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            // https://github.com/Kotlin/kotlinx.coroutines#avoiding-including-the-debug-infrastructure-in-the-resulting-apk
            excludes += "DebugProbesKt.bin"
        }
    }
    buildToolsVersion = "35.0.0"
//    room {
//        schemaDirectory("$projectDir/schemas")
//    }
}

dependencies {
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//    androidTestImplementation(libs.androidx.work.testing)

    coreLibraryDesugaring(libs.android.desugar.jdk.libs)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.leakcanary)

    implementation(libs.about.libraries.compose.m3)
    implementation(libs.about.libraries.core)
    implementation(libs.android.gms.play.services.location)
    implementation(libs.android.gms.play.services.maps)
    implementation(libs.android.material)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
//    implementation(libs.androidx.compose.runtime.livedata)
//    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.hilt.navigation.compose)
//    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
//    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.room.runtime)
//    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.google.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization)
    implementation(libs.timber)

    ksp(libs.google.hilt.compiler)
//    ksp(libs.androidx.room.compiler)
}