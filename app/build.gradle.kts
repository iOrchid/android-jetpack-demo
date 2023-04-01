plugins {
	@Suppress("DSL_SCOPE_VIOLATION")// 这属于gradle和IDE产生的bug，不影响运行，但是使用体验不佳
	alias(libs.plugins.com.android.application)
	@Suppress("DSL_SCOPE_VIOLATION")
	alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
	namespace = "org.zhiwei.jetpack"
	compileSdk = 33

	defaultConfig {
		applicationId = "org.zhiwei.jetpack"
		minSdk = 24
		targetSdk = 33
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
	buildFeatures {
		viewBinding = true
	}
}

dependencies {

	implementation(libs.core.ktx)
	implementation(libs.appcompat)
	implementation(libs.material)
	implementation(libs.constraintlayout)
	implementation(libs.navigation.fragment.ktx)
	implementation(libs.navigation.ui.ktx)


	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.espresso.core)
}