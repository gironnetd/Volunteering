plugins {
    alias(libs.plugins.androidTest)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "org.com.onelittleangel.baselineprofile"
    compileSdk = 34

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    defaultConfig {
        minSdk = 28
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    targetProjectPath = ":androidApp"

    /*testOptions.managedDevices.devices {
        create<ManagedVirtualDevice>("pixel6Api34") {
            device = "Pixel 6"
            apiLevel = 34
            systemImageSource = "aosp"
        }
    }*/
}

// This is the configuration block for the Baseline Profile plugin.
// You can specify to run the generators on a managed devices or connected devices.
baselineProfile {
    //managedDevices += "pixel6Api34"
    useConnectedDevices = true
}

dependencies {
    implementation(libs.androidx.test.junit)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)

    // Adds dependencies to enable running Composition Tracing from Macrobenchmarks.
    // These dependencies are already included with Macrobenchmark, but we have them here to specify the version.
    // For more information on Composition Tracing, check https://developer.android.com/jetpack/compose/tooling/tracing.
    implementation(libs.tracing.perfetto)
    implementation(libs.tracing.perfetto.binary)
}