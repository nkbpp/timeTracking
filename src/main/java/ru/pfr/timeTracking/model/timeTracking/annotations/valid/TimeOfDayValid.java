package ru.pfr.timeTracking.model.timeTracking.annotations.valid;

import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Pattern(regexp = "^(morning)|(evening)$")
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeOfDayValid {
    String message() default "Invalid timeOfDay";
}
