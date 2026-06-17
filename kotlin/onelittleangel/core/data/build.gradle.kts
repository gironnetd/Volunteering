plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.realm)
}

// Workaround for https://youtrack.jetbrains.com/issue/KT-51970
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
            baseName = "data"
            isStatic = true
            binaryOption("bundleId", "com.onelittleangel.data")
        }
    }

    task("testClasses")

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.realm.library.base)
            implementation(libs.koin.core)
            implementation(projects.core.model)
            implementation(projects.core.cache)
            implementation(projects.core.remote)
            implementation(projects.core.datastore)
            implementation(libs.kotlinx.datetime)
        }
    }
}

android {
    namespace = "com.onelittleangel.data"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    ndkVersion = "26.2.11394342"
}
