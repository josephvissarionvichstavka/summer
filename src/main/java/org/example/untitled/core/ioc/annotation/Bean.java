package org.example.untitled.core.ioc.annotation;


import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean { // 注册为bean
    String value() default "";
}
