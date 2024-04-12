package org.example.summer.core.ioc.annotation;


import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Summer {
    String value() default "";
}
