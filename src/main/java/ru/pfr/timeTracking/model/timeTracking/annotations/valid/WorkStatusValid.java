package ru.pfr.timeTracking.model.timeTracking.annotations.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Pattern(regexp = "^(atwork)|(vacation)|(sickLeave)|(businessTrip)$")
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
public @interface WorkStatusValid {
    String message() default "Invalid status";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
