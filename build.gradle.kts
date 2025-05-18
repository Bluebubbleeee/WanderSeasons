// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false

}

buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.48")
        classpath ("com.android.tools.build:gradle:8.9.0")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}
