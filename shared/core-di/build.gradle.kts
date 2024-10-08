import org.gradle.internal.os.OperatingSystem

plugins {
    id("confily.multiplatform.library")
    id("confily.quality")
}

android {
    namespace = "com.paligot.confily.core.di"
    buildFeatures {
        buildConfig = true
    }
}

kotlin {
    androidTarget()

    if (OperatingSystem.current().isMacOsX) {
        listOf(
            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries.framework {
                baseName = "SharedDi"
                isStatic = false
                export(libs.settings)
                export(projects.shared.core)
                export(projects.shared.models)
                export(projects.shared.uiModels)
                export(projects.shared.resources)
                // Required https://github.com/cashapp/sqldelight/issues/1442
                linkerOpts.add("-lsqlite3")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.shared.core)
                api(projects.shared.resources)
                implementation(libs.koin.core)
                implementation(libs.jetbrains.kotlinx.coroutines)
                implementation(libs.lyricist)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.koin.android)
            }
        }
        if (OperatingSystem.current().isMacOsX) {
            val iosArm64Main by getting
            val iosSimulatorArm64Main by getting
            val iosMain by creating {
                dependsOn(commonMain)
                iosArm64Main.dependsOn(this)
                iosSimulatorArm64Main.dependsOn(this)
            }
        }
    }
}
