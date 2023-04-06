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
include(":databinding")
include(":kotlin")
include(":livedata")
include(":lifecycle")
include(":mvvm")
include(":mvi")
include(":navigation")
include(":paging")
include(":room")
include(":viewmodel")
include(":work")
