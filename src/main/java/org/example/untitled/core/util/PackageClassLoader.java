package org.example.untitled.core.util;

import org.apache.commons.lang3.ClassLoaderUtils;
import org.example.untitled.core.ioc.annotation.Bean;
import org.example.untitled.core.ioc.annotation.Summer;
import org.example.untitled.mvc.annotation.Controller;
import org.example.untitled.orm.annotation.Mapper;
import org.example.untitled.orm.annotation.MysqlConfig;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PackageClassLoader {
    private final static Map<Class, List<Class<?>>> packageClass = new ConcurrentHashMap<>();  // 维护注解和class注册列表的数据

    public void add(Class<? extends Annotation> annotation ,Class<?> aClass){
        List<Class<?>> list = new ArrayList<>();
        if (packageClass.get(annotation) != null){ // 如果这个注解存在
            list = packageClass.get(annotation); // 顶这个注解的所有类
        }
        list.add(aClass); // 加进去
        packageClass.put(annotation,list); // 加回去
    }

    public PackageClassLoader(Set<String> packageSet){
        for (String basePackage : packageSet) {
            List<Class<?>> classes = ClassLoadUtil.loadClass(basePackage, false); // 返回这个包的所有类
            classes.forEach(aClass -> {
                if (aClass.getAnnotation(Bean.class) != null){
                    add(Bean.class,aClass);
                }
                if (aClass.getAnnotation(Controller.class) != null){
                    add(Controller.class,aClass);
                }
                if (aClass.getAnnotation(Mapper.class) != null){
                    add(Mapper.class,aClass);
                }
                if (aClass.getAnnotation(MysqlConfig.class) != null){
                    add(MysqlConfig.class,aClass);
                }
                // 后续补充
            });
        }
    }
    public Map<Class,List<Class<?>>> getAll(){
        return packageClass;
    }

}
