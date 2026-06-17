plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
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
            baseName = "foryou"
            isStatic = true
            binaryOption("bundleId", "com.onelittleangel.foryou")
        }
    }

    task("testClasses")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
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
            implementation(libs.koin.core)
            implementation(projects.core.model)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.cache)
            implementation(projects.core.datastore)
            implementation(projects.core.ui)
            implementation(projects.core.designsystem)
            implementation(projects.core.datastore)
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.datetime)
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain.get())
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(projects.core.cache)
            }
        }
    }
}

android {
    namespace = "com.onelittleangel.foryou"
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
        //resources.excludes += "/META-INF/LGPL2.1"
    }

    buildTypes {
        /*getByName("debug") {
            //isDebuggable = false
            signingConfig = signingConfigs.getByName("debug")
        }*/

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

    dependencies {
        debugImplementation(libs.androidx.compose.ui.tooling)
        implementation(libs.androidx.profileinstaller)
        androidTestImplementation(libs.androidx.benchmark.macro.junit4)
    }
}
