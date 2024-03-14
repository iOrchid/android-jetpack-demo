plugins {
	alias(libs.plugins.com.android.library)
	alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
	namespace = "org.zhiwei.jetpack.work"
	compileSdk = 34

	defaultConfig {
		minSdk = 24

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
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

	// android official libs version
	implementation(libs.activity.ktx)
	implementation(libs.appcompat)
	implementation(libs.constraintlayout)
	implementation(libs.core.ktx)
	implementation(libs.fragment.ktx)
	implementation(libs.material)
	implementation(libs.recyclerview)
	implementation(libs.swiperefreshlayout)


	// androidx 或 google 相关库

	implementation(libs.androidx.dataStore.core)
	implementation(libs.androidx.dataStore.preferences)
	implementation(libs.androidx.drawerlayout)

	implementation(libs.androidx.metrics)
	implementation(libs.androidx.preference)
	implementation(libs.androidx.profileinstaller)
	implementation(libs.androidx.startup)
	implementation(libs.androidx.tracing)
	implementation(libs.androidx.webkit)

	// Jetpack Components libs version
	implementation(libs.lifecycle.livedata.ktx)
	implementation(libs.lifecycle.viewmodel.ktx)
	implementation(libs.navigation.fragment.ktx)
	implementation(libs.navigation.ui.ktx)
	implementation(libs.work.runtime.ktx)

	// jetbrains official libs version
	implementation(libs.kotlin.stdlib)
	implementation(libs.kotlinx.coroutines.android)
	implementation(libs.kotlinx.datetime)
	implementation(libs.kotlinx.serialization.json)

	// 常用知名开源库
	implementation(libs.blankj.utils)
	implementation(libs.okhttp.logging)
	implementation(libs.retrofit.core)
	implementation(libs.retrofit.kotlin.serialization)
	implementation(libs.retrofit2.converter.gson)

	//test libs version
	testImplementation(libs.turbine)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.espresso.core)
}