import dev.icerock.gradle.MRVisibility

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.moko.multiplatform)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.onelittleangel.designsystem" // required
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
            baseName = "designsystem"
            isStatic = true
            binaryOption("bundleId", "com.onelittleangel.designsystem")
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
            api(libs.moko.resources)
            api(libs.moko.resources.compose)
            implementation(projects.core.model)
        }
    }
}

android {
    namespace = "com.onelittleangel.designsystem"
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
dependencies {
    implementation(libs.androidx.foundation.layout.android)
}
