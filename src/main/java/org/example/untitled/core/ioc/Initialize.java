package org.example.untitled.core.ioc;

import org.example.untitled.core.ioc.annotation.Bean;
import org.example.untitled.core.ioc.annotation.Summer;
import org.example.untitled.core.ioc.bean.InitFactory;
import org.example.untitled.core.ioc.contest.BeansContext;
import org.example.untitled.core.util.PackageClassLoader;
import org.example.untitled.mvc.annotation.Controller;
import org.example.untitled.orm.annotation.Mapper;
import org.example.untitled.orm.annotation.MysqlConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Initialize {
    public static void init(Set<String> packageNames){
        PackageClassLoader loader = new PackageClassLoader(packageNames); // 对包进行装载
        Map<Class, List<Class<?>>> all = loader.getAll();
        InitFactory factory = new InitFactory();
        Class[] classes = new Class[]{Bean.class, Controller.class, Mapper.class, MysqlConfig.class
                ,/* 后续可以添加 ,要起动的类名*/};
        for (Class aClass : classes) {
            factory.init(aClass).init(all.get(aClass)); // 都添加进去
        }
    }
    private static void injection(Field field,Object o){ // 依赖注入
        Object bean;
        if (field.isAnnotationPresent(Summer.class)){ // 是否是主类
            bean = BeansContext.getBean(field.getType().getName()); // 返回主类实例
            Map<String, Object> beans = BeansContext.getBeans(); // 全部实例
            List<Class<?>> classes = new ArrayList<>();
            if (beans.get(field.getType().getName()) == null){ // 如果bean里面没有这个类
                beans.forEach((k,c) -> {
                    if (field.getType().isAssignableFrom(c.getClass())){ // 类是否调用该字段
                        classes.add(c.getClass()); // 添加进去
                    }
                });
            }
            if (classes.size() == 1){
                bean = BeansContext.getBean(classes.get(0).getName());
            }
            try {
                field.setAccessible(true); // 设置成允许访问
                field.set(o,bean); // 将bean注入回字段
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public static void interFlow(){ // 依赖注入
        Map<String, Object> beans = BeansContext.getBeans();
        beans.forEach((k,v) -> {
            Class<?> aClass = v.getClass();  // 实例的类
            while (!aClass.equals(Object.class)){ // 遍历
                Field[] declaredFields = aClass.getDeclaredFields(); // 字段列表
                for (Field field : declaredFields) {
                    injection(field,v); // 每个字段都初始化
                }
                aClass = aClass.getSuperclass(); // 转化成父类
            }
        });
    }
}
