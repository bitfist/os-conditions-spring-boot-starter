import git.semver.plugin.changelog.ChangeLogFormatter
import git.semver.plugin.changelog.ChangeLogTextFormatter
import org.gradle.process.internal.ExecException
import java.io.ByteArrayOutputStream

plugins {
    `java-library`
    jacoco
    `maven-publish`
    id("com.github.jmongard.git-semver-plugin") version "0.14.0"
}

//region Dependencies
repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // BOMs
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.4.1"))

    // Dependencies
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
//endregion

//region Java configuration
java {
    withSourcesJar()
    withJavadocJar()

    sourceCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        csv.required = true
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
        attributes["Automatic-Module-Name"] = project.name.replace('-', '.')
    }

    from("LICENSE") {
        into("META-INF")
        rename { "LICENSE_${archiveBaseName.get()}" }
    }
}
//endregion

//region Semantic versioning
semver {
    patchPattern = "\\A(fix|chore|docs|refactor|ci|build|test|deps)(?:\\([^()]+\\))?:"

    val server = System.getenv("GITHUB_SERVER_URL") ?: "https://github.com"
    val repository = System.getenv("GITHUB_REPOSITORY") ?: "bitfist/os-conditions-spring-boot-starter"
    val repositoryUrl = "$server/$repository"

    fun ChangeLogTextFormatter.appendChangeLongEntry() {
        val longHash = hash().take(40)
        val shortHash = longHash.take(8)

        append("- [$shortHash]($repositoryUrl/commit/$longHash) ").append(scope()).appendLine(header())
    }

    changeLogFormat = ChangeLogFormatter {
        constants.headerTexts["refactor"] = "### Refactorings \uD83D\uDD28"

        appendLine(constants.header).appendLine()

        withType("release") {
            skip()
        }

        // breaking
        withBreakingChanges {
            appendLine(constants.breakingChange)
            formatChanges {
                appendChangeLongEntry()
            }
            appendLine()
        }

        // fix, feat, refactor
        withType(types = arrayOf("feat", "fix", "refactor")) {
            appendLine(constants.headerTexts[groupKey])
            with({ constants.headerTexts.containsKey(it.scope) }) {
                formatChanges {
                    appendChangeLongEntry()
                }
            }
            formatChanges {
                appendChangeLongEntry()
            }
            appendLine()
        }

        // chores and other known changes
        groupBySorted({ constants.headerTexts[it.scope] ?: constants.headerTexts[it.type] }) {
            appendLine(groupKey)
            with({ constants.headerTexts.containsKey(it.scope) }) {
                formatChanges {
                    appendChangeLongEntry()
                }
            }
            formatChanges {
                appendChangeLongEntry()
            }
            appendLine()
        }

        // other unknown changes
        otherwise {
            appendLine(constants.otherChange)
            formatChanges {
                appendChangeLongEntry()
            }
            appendLine()
        }

        appendLine(constants.footer)
    }
}

// look up git tag
val gitTagVersion: String = ByteArrayOutputStream().use { outputStream ->
    project.exec {
        commandLine("git", "tag", "--points-at", "HEAD")
        standardOutput = outputStream
    }
    outputStream.toString().trim()
}

val semanticVersionRegex = "\\d+\\.\\d+\\.\\d+".toRegex()

version = if (gitTagVersion.matches(semanticVersionRegex)) {
    gitTagVersion // version provided by git tag
} else {
    semver.version // version provided by the semantic versioning plugin
}
//endregion

//region Publishing
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String

            pom {
                name.set("Starter for OS detection.")
                description.set("This is a Spring Boot Starter for OS detection.")
                url.set("https://github.com/bitfist/os-conditions-spring-boot-starter")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("bitfist")
                        name.set("bitfist")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/bitfist/os-conditions-spring-boot-starter.git")
                    developerConnection.set("scm:git:ssh://github.com:bitfist/os-conditions-spring-boot-starter.git")
                    url.set("https://github.com/bitfist/os-conditions-spring-boot-starter")
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/bitfist/os-conditions-spring-boot-starter")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
//endregion