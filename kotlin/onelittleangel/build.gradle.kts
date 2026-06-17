plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.moko.multiplatform) apply false
    alias(libs.plugins.realm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.wire) apply false
    alias(libs.plugins.molecule) apply false
    alias(libs.plugins.androidTest) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.baselineprofile) apply false
}

buildscript {
    repositories {
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        mavenLocal()

        /*// workaround for https://youtrack.jetbrains.com/issue/KT-51379
        exclusiveContent {
            forRepository {
                ivy("https://download.jetbrains.com/kotlin/native/builds") {
                    name = "Kotlin Native"
                    patternLayout {

                        // example download URLs:
                        // https://download.jetbrains.com/kotlin/native/builds/releases/1.7.20/linux-x86_64/kotlin-native-prebuilt-linux-x86_64-1.7.20.tar.gz
                        // https://download.jetbrains.com/kotlin/native/builds/releases/1.7.20/windows-x86_64/kotlin-native-prebuilt-windows-x86_64-1.7.20.zip
                        // https://download.jetbrains.com/kotlin/native/builds/releases/1.7.20/macos-x86_64/kotlin-native-prebuilt-macos-x86_64-1.7.20.tar.gz
                        listOf(
                            "macos-x86_64",
                            "macos-aarch64",
                            "osx-x86_64",
                            "osx-aarch64",
                            "linux-x86_64",
                            "windows-x86_64",
                        ).forEach { os ->
                            listOf("dev", "releases").forEach { stage ->
                                artifact("$stage/[revision]/$os/[artifact]-[revision].[ext]")
                            }
                        }
                    }
                    metadataSources { artifact() }
                }
            }
            filter { includeModuleByRegex(".*", ".*kotlin-native-prebuilt.*") }
        }


        // Declare the Node.js & Yarn download repositories
        exclusiveContent {
            forRepository {
                ivy("https://nodejs.org/dist/") {
                    name = "Node Distributions at $url"
                    patternLayout { artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]") }
                    metadataSources { artifact() }
                    content { includeModule("org.nodejs", "node") }
                }
            }
            filter { includeGroup("org.nodejs") }
        }

        exclusiveContent {
            forRepository {
                ivy("https://github.com/yarnpkg/yarn/releases/download") {
                    name = "Yarn Distributions at $url"
                    patternLayout { artifact("v[revision]/[artifact](-v[revision]).[ext]") }
                    metadataSources { artifact() }
                    content { includeModule("com.yarnpkg", "yarn") }
                }
            }
            filter { includeGroup("com.yarnpkg") }
        }*/
    }

    dependencies {
        classpath(libs.moko.resources.gradle.plugin)
        classpath(libs.wire.plugin)
        classpath(libs.molecule.gradle.plugin)
        //classpath(libs.kotlin.native.gradle.plugin)
    }
}