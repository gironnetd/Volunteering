plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
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
            baseName = "analytics"
            isStatic = true
            binaryOption("bundleId", "com.onelittleangel.analytics")
        }
    }

    task("testClasses")

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
    }
}

android {
    namespace = "com.onelittleangel.analytics"
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
