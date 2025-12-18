import java.net.URI

plugins {
    `java-library`
    id("io.github.bitfist.gradle-github-support.release")
}

group = "io.github.bitfist"
description = "Spring Boot starter for OS detection."

//region Dependencies
repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // BOMs
    implementation(platform("org.springframework.boot:spring-boot-dependencies:4.0.1"))

    // Dependencies
    implementation("org.springframework.boot:spring-boot-autoconfigure")

    // Test dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
//endregion


gitHubRelease {
    projectName.set("OS conditions Spring Boot starter.")
    projectDescription.set("This is a Spring Boot Starter for OS detection.")
    developer.set("bitfist")
    licenseFile.set(projectDir.resolve("LICENSE.txt"))
    license.set("The Apache License, Version 2.0")
    licenseUri.set(URI("https://www.apache.org/licenses/LICENSE-2.0"))
}
