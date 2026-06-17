import dev.icerock.gradle.MRVisibility

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.realm)
    alias(libs.plugins.moko.multiplatform)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.onelittleangel.shared" // required
    multiplatformResourcesClassName = "Resources" // optional, default MR
    multiplatformResourcesVisibility = MRVisibility.Public // optional, default Public
    iosBaseLocalizationRegion = "fr" // optional, default "en"
    multiplatformResourcesSourceSet = "commonMain"  // optional, default "commonMain"
    disableStaticFrameworkWarning = true
}

kotlin {
    /*@OptIn(ExperimentalWasmDsl::class)
    wasmJs {
       browser()
    }*/

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
            baseName = "shared"
            isStatic = true
            binaryOption("bundleId", "org.com.onelittleangel.OnelittleAngel")
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
            implementation("dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.3.1")
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.stately.common)
            implementation(libs.molecule.runtime)
            implementation(libs.bundles.precompose)
            api(libs.moko.resources)
            api(libs.moko.resources.compose)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.multiplatform)
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.cache)
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

        androidMain.dependencies {
            implementation(libs.library.base)
            implementation(libs.kotlinx.coroutines.core)
            implementation( libs.koin.core)
            implementation( libs.koin.android)
        }
    }
}

android {
    namespace = "com.onelittleangel.shared"
    compileSdk = 34
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":core:model"))
}
