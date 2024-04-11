package org.example.untitled.orm.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MysqlConfig {
    String value() default "";
    String name() default "";
}
