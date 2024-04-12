package org.example.summer.core.ioc.bean;

import org.example.summer.core.ioc.contest.BeansContext;
import org.example.summer.orm.MapperProxy;

import java.lang.reflect.Proxy;
import java.util.List;

public class MapperInit implements Init{
    @Override
    public void init(List<Class<?>> classes) {
        if (classes == null){
            return;
        }
        try {
            for (Class<?> aClass : classes) {
                // 创建代理对象
                Object o = Proxy.newProxyInstance(aClass.getClassLoader(),// 类加载器
                        new Class[]{aClass}, // 实现类列表
                        new MapperProxy()); // 代理对象调用处理器
                BeansContext.add(aClass.getName(),o);
            }
        }catch (Exception e){
            System.out.println("初始化mapper异常");
        }
    }

    @Override
    public void init(Class cla) {

    }
}
