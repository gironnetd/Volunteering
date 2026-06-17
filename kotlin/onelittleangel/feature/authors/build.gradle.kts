import dev.icerock.gradle.MRVisibility

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.moko.multiplatform)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.onelittleangel.authors" // required
    multiplatformResourcesClassName = "Resources" // optional, default MR
    multiplatformResourcesVisibility = MRVisibility.Public // optional, default Public
    iosBaseLocalizationRegion = "fr" // optional, default "en"
    multiplatformResourcesSourceSet = "commonMain"  // optional, default "commonMain"
    disableStaticFrameworkWarning = true
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "authors"
            isStatic = true
            binaryOption("bundleId", "com.onelittleangel.authors")
        }
    }

    task("testClasses")

    sourceSets {
        getByName("androidMain") {
            kotlin.srcDir("build/generated/moko/androidMain/src")
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.stately.common)
            implementation(libs.molecule.runtime)
            implementation(libs.bundles.precompose)
            implementation(projects.core.model)
            implementation(projects.core.common)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.datastore)
            implementation(projects.core.cache)
            implementation(projects.core.designsystem)
            implementation(projects.core.ui)
            implementation(libs.koin.core)
            api(libs.moko.resources)
            api(libs.moko.resources.compose)
        }
    }
}

android {
    namespace = "com.onelittleangel.authors"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    ndkVersion = "26.2.11394342"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packaging {
        // Multiple dependency bring these files in. Exclude them to enable
        // our test APK to build (has no effect on our AARs)
        resources.excludes += "/META-INF/AL2.0"
        resources.excludes += "/META-INF/LGPL2.1"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "benchmark-rules.pro"
            )
            // In real app, this would use its own release keystore
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(libs.androidx.foundation.android)
}
