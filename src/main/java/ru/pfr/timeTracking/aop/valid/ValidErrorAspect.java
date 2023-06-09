package ru.pfr.timeTracking.aop.valid;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;


@Component
@Aspect
public class ValidErrorAspect {

    //Запрос по аннотации на методе.
    @Pointcut("@annotation(ValidError)")
    public void validError() {
    }

    @Around(value = "validError() && args(.., errors)")
    public ResponseEntity<?> checkValidErrors(ProceedingJoinPoint joinPoint, Errors errors) {

        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors()
                    .stream()
                    .map(objectError ->
                            "field: " + ((FieldError) objectError).getField() +
                                    "\n message: " + objectError.getDefaultMessage())
                    .collect(Collectors.joining(", "))
                    , HttpStatus.BAD_REQUEST);
        } else {
            try {
                return (ResponseEntity<?>) joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

    }

}
