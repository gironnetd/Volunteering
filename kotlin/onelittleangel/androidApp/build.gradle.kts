plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.jetbrains.compose)
}

android {
    namespace = "org.com.onelittleangel"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    ndkVersion = "26.2.11394342"

    androidResources {
        additionalParameters.add("--warn-manifest-validation")
    }

    defaultConfig {
        applicationId = "org.com.onelittleangel"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 5
        versionName = "5.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn"
        )
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            matchingFallbacks += listOf("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // In real app, this would use its own release keystore
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.materialIconsExtended)
    implementation(compose.material3)
    implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.3.1")
    implementation(compose.ui)
    implementation(compose.components.resources)

    implementation(libs.androidx.profileinstaller)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom.v20240202))
    androidTestImplementation(libs.androidx.compose.ui.test)
    "baselineProfile"(project(":baselineprofile"))
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)

    implementation(libs.realm.library.base)
    implementation(libs.kotlinx.coroutines.core) // If using coroutines with the SDK
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(projects.core.designsystem)
    implementation(projects.feature.explore)
    implementation(projects.feature.recents)
    implementation(projects.feature.bookmarks)
    implementation(projects.feature.foryou)
    implementation(projects.feature.authors)
    implementation(projects.feature.books)
    implementation(projects.feature.faiths)
    implementation(projects.feature.themes)
    implementation(projects.feature.settings)
    implementation(projects.shared)
}