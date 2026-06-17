import dev.icerock.gradle.MRVisibility

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.moko.multiplatform)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.onelittleangel.ui" // required
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
            baseName = "ui"
            isStatic = true
            binaryOption("bundleId", "com.onelittleangel.ui")
        }
    }

    task("testClasses")

    sourceSets {
        getByName("androidMain") {
            kotlin.srcDir("build/generated/moko/androidMain/src")
        }

        commonMain.dependencies {
            implementation(projects.core.designsystem)
            implementation(libs.koin.core)
            implementation(projects.core.model)
            implementation(projects.core.domain)
            implementation(projects.core.cache)
            implementation(projects.core.designsystem)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.datetime)
            implementation(libs.stately.common)
            implementation(libs.molecule.runtime)
            implementation(libs.bundles.precompose)
            api(libs.moko.resources)
            api(libs.moko.resources.compose)
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
                implementation(projects.core.cache)
            }
        }
    }
}

android {
    namespace = "com.onelittleangel.ui"
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
