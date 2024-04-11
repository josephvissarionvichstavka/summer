package org.example.untitled.core.ioc.annotation;


import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SummerBoot {
    String value() default "";
}
