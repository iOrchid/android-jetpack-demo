pluginManagement {
	repositories {
		google()
		maven("https://maven.aliyun.com/repository/public")
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
		maven("https://maven.aliyun.com/repository/public")
		mavenCentral()
	}
}

rootProject.name = "Jetpack"
include(":app")
include(":jetpack:databinding")
include(":kotlin")
include(":compose")
