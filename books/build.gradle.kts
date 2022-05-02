plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 23
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildFeatures {
        viewBinding = true
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
    //Visibility
    implementation(project(":core"))

    //Kotlin
    implementation(libs.kotlinStdLib)

    //ConstraintLayout
    implementation(libs.constraintLayout)

    ///Material
    implementation(libs.material)

    //Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)

    //FragmentKtx
    implementation(libs.fragmentKtx)

    //Hilt
    implementation(libs.hilt)
    kapt(libs.hiltAndroidCompiler)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.okhttp3)
    implementation(libs.loginInterceptor)

    //Gson
    implementation(libs.gson)
    implementation(libs.gsonConverter)

    //Coroutines
    implementation(libs.coroutinesAndroid)
    testImplementation(libs.coroutinesTest)

    //Room
    implementation(libs.room)
    implementation(libs.roomKtx)
    kapt(libs.roomCompiler)

    //Glide
    implementation(libs.glide)
    kapt(libs.glideCompiler)

    //JUnit
    testImplementation(libs.jUnit)

    //Mockito
    testImplementation(libs.mockito)
}