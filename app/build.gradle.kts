import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.about.libraries)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "me.dizzykitty3.androidtoolkitty"
    // remember to change the target api in manifest xml file
    compileSdk = 36
    buildToolsVersion = "36.0.0"

    buildFeatures {
        buildConfig = true
        compose = true
    }
    defaultConfig {
        applicationId = "me.dizzykitty3.androidtoolkitty"
        // minSdkVersion 21 cannot be smaller than version 23 declared in library [androidx.navigationevent:navigationevent-android:1.0.0]
        minSdk = 23
        targetSdk = compileSdk
        versionCode = 11500
        versionName = "1.15.0"

        resValue("string", "app_name", "ToolKitty")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ".dev"
            versionNameSuffix = ".dev"
            resValue("string", "app_name", "ToolKitty dev")
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    applicationVariants.all {
        outputs.all {
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl)
                .outputFileName = "android-toolkitty-${versionName}.apk"
        }
    }
    composeCompiler {
        reportsDestination = layout.buildDirectory.dir("compose_compiler")
        // https://developer.android.com/develop/ui/compose/compiler#configuration-options
//        stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
    }
    // Since Kotlin 2.2.0, https://kotlinlang.org/docs/gradle-compiler-options.html#migrate-from-kotlinoptions-to-compileroptions
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
            extraWarnings = true
        }
    }
    compileOptions {
        // https://developer.android.com/studio/write/java8-support#library-desugaring
        isCoreLibraryDesugaringEnabled = true // Java 8+ API desugaring support
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        encoding = "UTF-8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            // https://github.com/Kotlin/kotlinx.coroutines#avoiding-including-the-debug-infrastructure-in-the-resulting-apk
            excludes += "DebugProbesKt.bin"
        }
    }
    androidResources {
        // https://developer.android.com/guide/topics/resources/app-languages#auto-localeconfig
        @Suppress("UnstableApiUsage")
        generateLocaleConfig = true // Per-app language preferences
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugar.jdk.libs)

    debugImplementation(libs.leakcanary)

    implementation(libs.about.libraries.compose.m3)
    implementation(libs.about.libraries.core)
    implementation(libs.android.gms.play.services.maps)
    implementation(libs.android.material)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.window.size)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.google.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)

    ksp(libs.google.hilt.android.compiler)
}