package io.github.bitfist.springframework.boot.autoconfigure.os;

import io.github.bitfist.springframework.boot.os.OperatingSystem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class OperatingSystemAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    @DisplayName("Testing OS environments")
    @ParameterizedTest(name = "given os.name={0}, when the application context is configured, then {1} should be active")
    @MethodSource("osTestParameters")
    void givenOSEnvironment_whenContextIsConfigured_thenExpectedBeanIsActive(
            String osName,
            OperatingSystem expectedOS
    ) {
        contextRunner
                .withUserConfiguration(OperatingSystemAutoConfiguration.class)
                .withPropertyValues("os.name=" + osName)
                .run(context -> {
                    assertThat(context)
                            .hasBean("operatingSystem")
                            .hasSingleBean(OperatingSystem.class);
                    OperatingSystem os = context.getBean("operatingSystem", OperatingSystem.class);
                    assertThat(os).isEqualTo(expectedOS);
                });
    }

    static Stream<Arguments> osTestParameters() {
        return Stream.of(
                arguments("Windows", OperatingSystem.WINDOWS),
                arguments("Linux", OperatingSystem.LINUX),
                arguments("FreeBSD", OperatingSystem.FREEBSD),
                arguments("OpenBSD", OperatingSystem.OPENBSD),
                arguments("MacOS", OperatingSystem.MACOS),
                arguments("Darwin", OperatingSystem.MACOS),
                arguments("AIX", OperatingSystem.AIX),
                arguments("SunOS", OperatingSystem.UNKNOWN)
        );
    }
}