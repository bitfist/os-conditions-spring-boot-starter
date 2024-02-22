[![Gradle build](https://github.com/bitfist/os-conditions-spring-boot-starter/actions/workflows/test.yml/badge.svg)](https://github.com/bitfist/os-conditions-spring-boot-starter/actions/workflows/test.yml)
![Coverage](.github/badges/jacoco.svg)
![Branches](.github/badges/branches.svg)

# Spring Boot Operating System Conditions

Spring Boot Operating System Conditions provides `@Conditional`s for OS detection.

Table of contents
=================

* [Features](#features)
* [Usage](#usage)
  * [@ConditionalOnOperatingSystem](#conditionalonoperatingsystem)
  * [OperatingSystem bean](#operatingsystem-bean)
* [Dependency](#dependency)

## Features

* [Autoconfiguration](src/main/java/io/github/bitfist/springframework/boot/autoconfigure/os/OperatingSystemAutoConfiguration.java)
* Bean of type [io.github.bitfist.springframework.boot.OperatingSystem](src/main/java/io/github/bitfist/springframework/boot/os/OperatingSystem.java) named `operatingSystem`
* Conditional [@ConditionalOnOperatingSystem](src/main/java/io/github/bitfist/springframework/boot/autoconfigure/condition/ConditionalOnOperatingSystem.java) annotation

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

## Dependency

The [GitHub documentation](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#using-a-published-package)
explains in detail how to use GitHub packages.