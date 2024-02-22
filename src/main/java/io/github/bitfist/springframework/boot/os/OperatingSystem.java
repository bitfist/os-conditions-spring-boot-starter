package io.github.bitfist.springframework.boot.os;

import org.springframework.core.env.Environment;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * Simple detection for operating systems.
 *
 * @author bitfist
 * @since 3.2.2
 */
public enum OperatingSystem {

    /**
     * <strong>Microsoft Windows</strong>
     * <a href="https://www.microsoft.com/windows">https://www.microsoft.com/windows</a>
     */
    WINDOWS {
        @Override
        public boolean isDetected(Environment environment) {
            return OperatingSystem.getOS(environment).contains("win");
        }
    },
    /**
     * <strong>Linux</strong>
     * <a href="https://www.kernel.org/">https://www.kernel.org/</a>
     */
    LINUX {
        @Override
        public boolean isDetected(Environment environment) {
            return OperatingSystem.getOS(environment).contains("nux");
        }
    },
    /**
     * <strong>Apple macOS</strong>
     * <a href="https://www.apple.com/macos">https://www.apple.com/macos</a>
     */
    MACOS {
        @Override
        public boolean isDetected(Environment environment) {
            String os = OperatingSystem.getOS(environment);
            return os.contains("mac") || os.contains("darwin");
        }
    },
    /**
     * <strong>FreeBsd</strong>
     * <a href="https://www.freebsd.org">https://www.freebsd.org</a>
     */
    FREEBSD {
        @Override
        public boolean isDetected(Environment environment) {
            return OperatingSystem.getOS(environment).contains("freebsd");
        }
    },
    /**
     * <strong>OpenBSD</strong>
     * <a href="https://www.openbsd.org/">https://www.openbsd.org/</a>
     */
    OPENBSD {
        @Override
        public boolean isDetected(Environment environment) {
            return OperatingSystem.getOS(environment).contains("openbsd");
        }
    },
    /**
     * <strong>AIX</strong>
     * <a href="https://www.ibm.com/products/aix">https://www.ibm.com/products/aix</a>
     */
    AIX {
        @Override
        public boolean isDetected(Environment environment) {
            return OperatingSystem.getOS(environment).contains("aix");
        }
    },
    /**
     * <strong>Unknown OS</strong>
     */
    UNKNOWN {
        @Override
        public boolean isDetected(Environment environment) {
            return KNOWN_OPERATING_SYSTEMS.stream().noneMatch(os -> os.isDetected(environment));
        }
    };

    private static final String UNKNOWN_OS = "unknown";

    /**
     * This set contains all known {@link OperatingSystem}s (all except {@link OperatingSystem#UNKNOWN}).
     */
    private static final Set<OperatingSystem> KNOWN_OPERATING_SYSTEMS = Stream
            .of(OperatingSystem.values())
            .filter(os -> !UNKNOWN.equals(os))
            .collect(toSet());

    /**
     * Check the operating system of the environment.
     *
     * @param environment the environment to check
     * @return true, if detected
     */
    public abstract boolean isDetected(Environment environment);

    /**
     * Helper method to retrieve the operating system from the environment in a null-safe way.
     *
     * @param environment the current environment
     * @return the operating system or {@link #UNKNOWN_OS}
     */
    private static String getOS(Environment environment) {
        return environment.getProperty("os.name", UNKNOWN_OS).toLowerCase(Locale.ENGLISH);
    }
}
