// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    //用于替换kapt的，ksp插件实现注解处理；1，项目build.gradle中添加plugins
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.androidDynamicFeature) apply false
    //compose kotlin 2.0开始插件plugin方式
    alias(libs.plugins.compose.compiler) apply false
}
//为了navigation的safe args，添加如下方式
buildscript {
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}