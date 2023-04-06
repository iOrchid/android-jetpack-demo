// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
	@Suppress("DSL_SCOPE_VIOLATION")// TODO: Remove when updating to Gradle 8.1 (https://github.com/gradle/gradle/issues/22797)
	alias(libs.plugins.com.android.application) apply false
	@Suppress("DSL_SCOPE_VIOLATION")
	alias(libs.plugins.com.android.library) apply false
	@Suppress("DSL_SCOPE_VIOLATION")
	alias(libs.plugins.org.jetbrains.kotlin.android) apply false
	//用于替换kapt的，ksp插件实现注解处理；1，项目build.gradle中添加plugins
	// 可使用旧的id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false形式
	@Suppress("DSL_SCOPE_VIOLATION")//如果不想临时处理这个IDE报错，
	alias(libs.plugins.ksp) apply false
}