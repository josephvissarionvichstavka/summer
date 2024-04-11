package org.example.untitled.core.ioc.annotation;


import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Summer {
    String value() default "";
}
