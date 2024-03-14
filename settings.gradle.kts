pluginManagement {
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
	}
}
dependencyResolutionManagement {
	@Suppress("UnstableApiUsage")
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	@Suppress("UnstableApiUsage")
	repositories {
		google()
		mavenCentral()
	}
}

rootProject.name = "Jetpack"
include(":app")
include(":jetpack:databinding")
include(":jetpack:lifecycle")
include(":jetpack:livedata")
include(":jetpack:navigation")
include(":jetpack:paging")
include(":jetpack:room")
include(":jetpack:viewmodel")
include(":jetpack:work")
include(":kotlin")
include(":mvi")
include(":mvvm")
