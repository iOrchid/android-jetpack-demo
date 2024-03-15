plugins {
	alias(libs.plugins.androidDynamicFeature)
	alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
	namespace = "org.zhiwei.kotlin"
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
}

dependencies {

	implementation(libs.material)
	implementation(libs.constraintlayout)
	implementation(libs.kotlinx.coroutines.android)

	implementation(project(":app"))

	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.espresso.core)
}