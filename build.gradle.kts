
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:3.2.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.0") // Add this line


    }
}

allprojects {
    repositories {
        maven ( "https://jitpack.io")
        maven("https://maven.google.com")
   }
}
