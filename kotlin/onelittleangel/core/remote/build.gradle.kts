plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
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
            baseName = "remote"
            isStatic = true
            binaryOption("bundleId", "com.onelittleangel.remote")
        }
    }

    task("testClasses")

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.firebase.firestore)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.koin.core)
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain.get())
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.onelttleangel.remote"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    ndkVersion = "26.2.11394342"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
