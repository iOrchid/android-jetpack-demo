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
 