package io.github.bitfist.springframework.boot.autoconfigure.condition;

import io.github.bitfist.springframework.boot.os.OperatingSystem;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

/**
 * {@link Condition} that checks for a required {@link OperatingSystem}.
 *
 * @author bitfist
 * @see ConditionalOnOperatingSystem
 * @since 3.2.2
 */
class OnOperatingSystemCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        var attributes = Objects.requireNonNull(
                metadata.getAnnotationAttributes(ConditionalOnOperatingSystem.class.getName()),
                "@ConditionalOnOperatingSystem missing"
        );
        OperatingSystem operatingSystem = (OperatingSystem) attributes.get("value");
        return getMatchOutcome(context.getEnvironment(), operatingSystem);
    }

    private ConditionOutcome getMatchOutcome(Environment environment, OperatingSystem operatingSystem) {
        String name = operatingSystem.name();
        ConditionMessage.Builder message = ConditionMessage.forCondition(ConditionalOnOperatingSystem.class);
        if (operatingSystem.isDetected(environment)) {
            return ConditionOutcome.match(message.foundExactly(name));
        }
        return ConditionOutcome.noMatch(message.didNotFind(name).atAll());
    }
}
