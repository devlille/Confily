import extensions.stringBuildConfigField
import extensions.toProperties

plugins {
    id("conferences4hall.android.library")
    id("conferences4hall.quality")
}

android {
    namespace = "org.gdglille.devfest.android.core.sample"

    val appProps = rootProject.file("androidApp/app.properties").toProperties()
    buildTypes {
        getByName("release") {
            stringBuildConfigField("BASE_URL", appProps)
            stringBuildConfigField("EVENT_ID", appProps)
            stringBuildConfigField("CONTACT_MAIL", appProps)
            stringBuildConfigField("FIREBASE_PROJECT_ID", appProps)
            stringBuildConfigField("FIREBASE_APP_ID", appProps)
            stringBuildConfigField("FIREBASE_API_KEY", appProps)
            stringBuildConfigField("DEFAULT_EVENT", appProps)
        }
        getByName("debug") {
            stringBuildConfigField("BASE_URL", appProps)
            stringBuildConfigField("EVENT_ID", appProps)
            stringBuildConfigField("CONTACT_MAIL", appProps)
            stringBuildConfigField("FIREBASE_PROJECT_ID", appProps)
            stringBuildConfigField("FIREBASE_APP_ID", appProps)
            stringBuildConfigField("FIREBASE_API_KEY", appProps)
            stringBuildConfigField("DEFAULT_EVENT", appProps)
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.shared.core)
    implementation(projects.shared.coreDi)
    implementation(libs.google.material)

    implementation(libs.androidx.appcompat)

    api(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.workmanager)

    api(libs.coil.compose)
    api(libs.coil.svg)

    implementation(libs.openfeedback.viewmodel)
}