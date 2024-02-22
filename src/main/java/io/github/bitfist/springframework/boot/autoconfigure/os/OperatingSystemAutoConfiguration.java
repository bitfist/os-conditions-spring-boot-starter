package io.github.bitfist.springframework.boot.autoconfigure.os;

import io.github.bitfist.springframework.boot.autoconfigure.condition.ConditionalOnOperatingSystem;
import io.github.bitfist.springframework.boot.os.OperatingSystem;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration to provide the {@link OperatingSystem} as bean.
 */
@AutoConfiguration
public class OperatingSystemAutoConfiguration {

    /**
     * OS bean configuration interface
     */
    interface OperatingSystemConfiguration {

        OperatingSystem operatingSystem();
    }

    /**
     * Activated on Windows systems.
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnOperatingSystem(OperatingSystem.WINDOWS)
    public static class WindowsConfiguration implements OperatingSystemConfiguration {
        @Bean
        @ConditionalOnMissingBean
        @Override
        public OperatingSystem operatingSystem() {
            return OperatingSystem.WINDOWS;
        }
    }

    /**
     * Activated on Linux systems.
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnOperatingSystem(OperatingSystem.LINUX)
    public static class LinuxConfiguration implements OperatingSystemConfiguration {
        @Bean
        @ConditionalOnMissingBean
        @Override
        public OperatingSystem operatingSystem() {
            return OperatingSystem.LINUX;
        }
    }

    /**
     * Activated on MacOS/Darwin systems.
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnOperatingSystem(OperatingSystem.MACOS)
    public static class MacOSConfiguration implements OperatingSystemConfiguration {
        @Bean
        @ConditionalOnMissingBean
        @Override
        public OperatingSystem operatingSystem() {
            return OperatingSystem.MACOS;
        }
    }

    /**
     * Activated on FreeBSD systems.
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnOperatingSystem(OperatingSystem.FREEBSD)
    public static class FreeBSDConfiguration implements OperatingSystemConfiguration {
        @Bean
        @ConditionalOnMissingBean
        @Override
        public OperatingSystem operatingSystem() {
            return OperatingSystem.FREEBSD;
        }
    }

    /**
     * Activated on OpenBSD systems.
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnOperatingSystem(OperatingSystem.OPENBSD)
    public static class OpenBSDConfiguration implements OperatingSystemConfiguration {
        @Bean
        @ConditionalOnMissingBean
        @Override
        public OperatingSystem operatingSystem() {
            return OperatingSystem.OPENBSD;
        }
    }

    /**
     * Activated on AIX systems.
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnOperatingSystem(OperatingSystem.AIX)
    public static class AixConfiguration implements OperatingSystemConfiguration {
        @Bean
        @ConditionalOnMissingBean
        @Override
        public OperatingSystem operatingSystem() {
            return OperatingSystem.AIX;
        }
    }

    /**
     * Activated on unknown operating systems.
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnOperatingSystem(OperatingSystem.UNKNOWN)
    public static class UnknownOsConfiguration implements OperatingSystemConfiguration {
        @Bean
        @ConditionalOnMissingBean
        @Override
        public OperatingSystem operatingSystem() {
            return OperatingSystem.UNKNOWN;
        }
    }
}
