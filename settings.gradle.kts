pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()

        maven {
            name = "gradle-github-support"
            url = uri("https://maven.pkg.github.com/bitfist/gradle-github-support")
            credentials {
                try {
                    username = settings.extra["GPR_USER"] as String?
                } catch (exception: ExtraPropertiesExtension.UnknownPropertyException) {
                    username = System.getenv("GITHUB_ACTOR") ?: throw IllegalArgumentException("GITHUB_ACTOR not set")
                }
                try {
                    password = settings.extra["GPR_KEY"] as String?
                } catch (exception: ExtraPropertiesExtension.UnknownPropertyException) {
                    password = System.getenv("GITHUB_TOKEN") ?: throw IllegalArgumentException("GITHUB_TOKEN not set")
                }
            }
        }
    }
    plugins {
        id("io.github.bitfist.gradle-github-support.release") version "0.2.2"
    }
}

rootProject.name = "os-conditions-spring-boot-starter"