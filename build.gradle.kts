// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
	@Suppress("DSL_SCOPE_VIOLATION")// TODO: Remove when updating to Gradle 8.1 (https://github.com/gradle/gradle/issues/22797)
	alias(libs.plugins.com.android.application) apply false
	@Suppress("DSL_SCOPE_VIOLATION")
	alias(libs.plugins.org.jetbrains.kotlin.android) apply false
}