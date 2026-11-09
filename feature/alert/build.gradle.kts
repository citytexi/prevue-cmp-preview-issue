import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    android {
        namespace = "com.citytexi.testapp.feature.alert"
        compileSdk = 37
        minSdk = 26
        compilerOptions.jvmTarget = JvmTarget.JVM_17
    }
    sourceSets {
        commonMain.dependencies {
            // Both are `implementation` — the usual choice for a feature module.
            // Neither reaches the app's compile classpath.
            implementation(project(":core:designsystem"))
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
        }
        androidMain.dependencies {
            // The renderer needs ComposeViewAdapter at runtime — same placement as a typical CMP feature module.
            implementation(libs.compose.ui.tooling)
        }
    }
}
