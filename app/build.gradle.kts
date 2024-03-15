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

    //使用动态模块的形式，用于隔离代码module的依赖
    dynamicFeatures += setOf(":jetpack:jetpack", ":kotlin", ":compose")

}

dependencies {
    //因为app使用的theme是material的
    implementation(libs.material)

    //compose 相关 ,因为有dynamic的组件，compose的，所以宿主模块也要有必要的依赖，否则会报错。
    //这里android闭包内不需要compose的compiler和buildfeature，如果是普通module的依赖，则需要主module也开启。
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)


    //test libs version
    testImplementation(libs.turbine)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)


}