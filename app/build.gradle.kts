plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.sycosoft.allsee"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sycosoft.allsee"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("dagger.hilt.android.internal.disableAndroidSuperclassValidation" to "ENABLED")
            }
        }
    }

    useLibrary("android.test.mock")

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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "mockito-extensions/org.mockito.plugins.MemberAccessor"
            merges += "mockito-extensions/org.mockito.plugins.MockMaker"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Constraint Layout
    implementation(libs.androidx.constraintlayout.compose)

    // Dagger
    implementation(libs.dagger)
    implementation(libs.dagger.android)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.compiler.android)

    // Data Store
    implementation(libs.androidx.datastore.preferences)

    // Mockito
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockwebserver)
    testImplementation(libs.mockwebserver)

    // MockK
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.mockk)

    // Retrofit and okHttp
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logginginterceptor)

    // Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)

    // Gson
    implementation(libs.gson)

    // Coroutines Test
    testImplementation(libs.kotlinx.coroutines.test)

    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    testImplementation(libs.androidx.room.testing)

    // Icons Extended
    implementation(libs.androidx.material.icons.extended)

    // Turbine Testing Library
    testImplementation(libs.turbine)
}