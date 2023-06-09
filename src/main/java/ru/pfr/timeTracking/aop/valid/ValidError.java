package ru.pfr.timeTracking.aop.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Используется для обработки ошибок при валидации в контроллерах средствами AOP
 * Важно! Аннотируемый метод должен содержать параметр Errors errors в последней позиции
 * после параметров аннотируемых @RequestPart или @RequestParam
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidError {
}
