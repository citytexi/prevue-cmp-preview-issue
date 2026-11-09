plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.citytexi.testapp"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.citytexi.testapp"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // The app reaches :feature:alert only. :core:designsystem and ui-tooling-preview are
    // `implementation` dependencies of :feature:alert, so they are NOT on the app's compile classpath.
    implementation(project(":feature:alert"))
    implementation(libs.androidx.activity.compose)
}
