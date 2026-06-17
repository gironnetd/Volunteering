import dev.icerock.gradle.MRVisibility

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.realm)
    alias(libs.plugins.moko.multiplatform)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.onelittleangel.cache" // required
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
            baseName = "cache"
            isStatic = true
            binaryOption("bundleId", "com.onelittleangel.cache")
        }
    }

    task("testClasses")

    sourceSets {
        getByName("androidMain") {
            kotlin.srcDir("build/generated/moko/androidMain/src")
        }

        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(compose.ui)
            implementation(libs.realm.library.base)
            implementation(libs.moko.resources)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.moko.resources.compose)
            implementation(libs.koin.core)
            implementation(projects.core.model)
            implementation(libs.kotlinx.datetime)
            implementation(projects.core.common)
        }

        androidMain {
            dependencies {
                implementation(libs.koin.android)
            }
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
    namespace = "com.onelittleangel.cache"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    ndkVersion = "26.2.11394342"

    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src/androidMain/assets")
            }
        }
    }
}

