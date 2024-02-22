package io.github.bitfist.springframework.boot.autoconfigure.condition;

import io.github.bitfist.springframework.boot.os.OperatingSystem;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * {@link Conditional @Conditional} that matches when the specified operating system is active.
 *
 * @author bitfist
 * @since 3.2.2
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnOperatingSystemCondition.class)
public @interface ConditionalOnOperatingSystem {

    /**
     * The {@link OperatingSystem operating system} that must be active.
     *
     * @return the expected operating system
     */
    OperatingSystem value();
}
