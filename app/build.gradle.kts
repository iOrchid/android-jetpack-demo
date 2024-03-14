plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "org.zhiwei.jetpack"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.zhiwei.jetpack"
        minSdk = 24
        targetSdk = 34
        versionCode = 200
        versionName = "2.0.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    //依赖其他模块
    implementation(project(":jetpack:databinding"))
    implementation(project(":jetpack:lifecycle"))
    implementation(project(":jetpack:livedata"))
    implementation(project(":jetpack:navigation"))
    implementation(project(":jetpack:paging"))
    implementation(project(":jetpack:room"))
    implementation(project(":jetpack:viewmodel"))
    implementation(project(":jetpack:work"))
    implementation(project(":kotlin"))

    //test libs version
    testImplementation(libs.turbine)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)


}