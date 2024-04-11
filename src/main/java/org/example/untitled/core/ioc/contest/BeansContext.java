package org.example.untitled.core.ioc.contest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeansContext {// bean实例管理器
    private static Map<String,Object> beans = new ConcurrentHashMap<>(32);// 管理32个实例

    public static void add(String name,Object o){
        beans.put(name,o);
    }
    public static Map<String ,Object> getBeans(){
        return beans;
    }
    public static Object getBean(String name){
        return beans.get(name);
    }
}
