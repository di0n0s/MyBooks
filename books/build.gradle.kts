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

    //Kotlin
    implementation(libs.kotlinStdLib)

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

    //Room
    implementation(libs.room)
    annotationProcessor(libs.roomCompiler)
}