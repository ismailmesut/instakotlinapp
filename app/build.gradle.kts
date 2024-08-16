plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ismailmesutmujde.instakotlinapp"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }


    defaultConfig {
        applicationId = "com.ismailmesutmujde.instakotlinapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

val fragment_version = "1.8.2"


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // bottom navigation view
    implementation ("com.github.ittianyu:BottomNavigationViewEx:1.2.4")

    implementation (libs.design)

    // circle image view
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation ("androidx.fragment:fragment-ktx:$fragment_version")

    // universal image loader
    implementation ("com.nostra13.universalimageloader:universal-image-loader:1.9.5")

    // eventbus
    implementation("org.greenrobot:eventbus:3.3.1")

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.database)

    // firebase ui
    implementation (libs.firebase.ui.auth)


}