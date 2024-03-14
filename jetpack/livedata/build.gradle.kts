plugins {
	alias(libs.plugins.com.android.library)
	alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
	namespace = "org.zhiwei.jetpack.livedata"
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

	implementation(libs.core.ktx)
	implementation(libs.appcompat)
	implementation(libs.material)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.espresso.core)
}