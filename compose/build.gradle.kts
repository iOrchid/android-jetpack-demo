plugins {
    alias(libs.plugins.androidDynamicFeature)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose")
}
android {
    namespace = "org.zhiwei.compose"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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

    buildFeatures { compose = true }

    //compose compiler 2.0使用plugin形式后，配置方式
    composeCompiler {
        enableStrongSkippingMode = true
    }

}

dependencies {

    implementation(project(":app"))

    implementation(libs.core.ktx)
    //compose 相关
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.runtime.livedata)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.constraintlayout)
    //material的compose的库，与material3的库不同，选择用一种风格即可。
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
//    implementation(libs.accompanist.systemuicontroller)

    //图片加载库 compose版本的
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.gif)


    //测试相关的库
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.annotation)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
}