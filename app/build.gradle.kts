plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "org.zhiwei.jetpack"
    compileSdk = 36

    defaultConfig {
        applicationId = "org.zhiwei.jetpack"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }

    //使用动态模块的形式，用于隔离代码module的依赖,本项目为了方便module划分和依赖关系处理，才使用了feature，
    // todo 但是会有一些副作用，比如运行时候，一些feature内需要的库，可能app的module没有，就会运行时错误，编译可能看不出来；
    //所以，正常项目就按照普通的module添加依赖就好。
    dynamicFeatures += setOf(":jetpack", ":kotlin", ":compose")
    //jetpack模块有dataBinding，所以这里要声明
    buildFeatures { dataBinding = true }

}

dependencies {
    //因为app使用的theme是material的,因为使用了dynamicFeature依赖的module，所以如果多个dynamic的模块有共同的依赖库，则app中需要添加
    implementation(libs.material)
    implementation(libs.constraintlayout)
    //接入jetpack的动态module，需要通用依赖
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.dynamic.feature)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.work.runtime.ktx)
    implementation(libs.blankj.utils)

    //⚠️ todo compose 相关 ,因为有dynamic的组件，compose的，所以宿主模块也要有必要的依赖，否则会报错。
    //上面的android闭包内不需要compose的compiler和buildFeature，如果是普通module的依赖，则需要主module也开启。
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.coil.kt.compose)


    //test libs version
    testImplementation(libs.turbine)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)


}