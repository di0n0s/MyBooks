plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.example.mybooks"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.mybooks.runner.HiltTestRunner"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    ///Visibility
    implementation(project(":books"))
    implementation(project(":core"))

    //Kotlin
    implementation(libs.kotlinStdLib)

    //AppCompat
    implementation(libs.appCompat)

    //Hilt
    implementation(libs.hilt)
    kapt(libs.hiltAndroidCompiler)

    //UI Test
    androidTestImplementation(libs.hiltAndroidTesting)
    kaptAndroidTest(libs.hiltAndroidCompiler)
    debugImplementation(libs.fragmentTesting)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.espressoContrib) {
        exclude(group = "com.google.android.material")
    }

    //Room
    androidTestImplementation(libs.room)
    androidTestImplementation(libs.roomKtx)
    kapt(libs.roomCompiler)


}