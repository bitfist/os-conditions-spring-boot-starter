![GitHub Release Plugin](https://img.shields.io/static/v1?label=GitHub&message=Release&color=blue&logo=github)
![License](https://img.shields.io/badge/License-Apache%20License%20Version%202.0-blue)
[![Gradle build](https://github.com/bitfist/os-conditions-spring-boot-starter/actions/workflows/test.yml/badge.svg)](https://github.com/bitfist/os-conditions-spring-boot-starter/actions/workflows/test.yml)
![Coverage](.github/badges/jacoco.svg)
![Branches](.github/badges/branches.svg)

# Spring Boot Operating System Conditions

Spring Boot Operating System Conditions provides `@Conditional`s for OS detection.

> [!IMPORTANT]
> This project uses dependencies provided from GitHub. You therefore need to set your GitHub user `GPR_USER` and
> personal access token `GPR_TOKEN` in your `~/.gradle/gradle.properties`

---

Table of contents
=================

* [Features](#features)
* [Usage](#usage)
  * [@ConditionalOnOperatingSystem](#conditionalonoperatingsystem)
  * [OperatingSystem bean](#operatingsystem-bean)
* [Dependency](#dependency)

---

## Features

* [Autoconfiguration](src/main/java/io/github/bitfist/springframework/boot/autoconfigure/os/OperatingSystemAutoConfiguration.java)
* Bean of type [io.github.bitfist.springframework.boot.OperatingSystem](src/main/java/io/github/bitfist/springframework/boot/os/OperatingSystem.java) named `operatingSystem`
* Conditional [@ConditionalOnOperatingSystem](src/main/java/io/github/bitfist/springframework/boot/autoconfigure/condition/ConditionalOnOperatingSystem.java) annotation

---

## Usage

### @ConditionalOnOperatingSystem
```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnOperatingSystem(OperatingSystem.WINDOWS)
public class WindowsConfiguration {
    // ...
}
```

### OperatingSystem bean
```java
@Configuration
public class SomeConfiguration {
    
    @Bean
    Object someBeanProducer(OperatingSystem operatingSystem) {
        // ...
    }
}
```

---

## Dependency

The [GitHub documentation](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#using-a-published-package)
explains in detail how to use GitHub packages.