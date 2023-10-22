plugins {
    id("conferences4hall.android.library")
    id("conferences4hall.android.library.compose")
    id("conferences4hall.quality")
}

android {
    namespace = "org.gdglille.devfest.android.theme.m3.partners.feature"
}

dependencies {
    implementation(projects.shared.core)
    implementation(projects.themeM3.partners.partnersUi)
    implementation(projects.themeM3.navigation)
    implementation(projects.themeM3.style.theme)

    implementation(libs.kotlinx.collections)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.tooling)
    implementation(libs.androidx.compose.lifecycle)
    implementation(libs.androidx.lifecycle.vm)

    implementation(platform(libs.google.firebase))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
}