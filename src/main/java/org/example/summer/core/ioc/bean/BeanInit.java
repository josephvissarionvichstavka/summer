package org.example.summer.core.ioc.bean;

import org.example.summer.core.ioc.annotation.Bean;
import org.example.summer.core.ioc.contest.BeansContext;

import java.util.List;

public class BeanInit implements Init{
    @Override
    public void init(List<Class<?>> classes) {  // bean的初始化
        if (classes == null){
            return;
        }
        for (Class<?> aClass : classes) {
            Bean annotation = aClass.getAnnotation(Bean.class);
            String name;  // bean的名字
            if (annotation.value().trim().length() > 0){ // 不为空
                name = annotation.value(); //name 就是value值
            }else {
                name = aClass.getName(); // name就是类名
            }
            try {
                BeansContext.add(name,aClass.newInstance()); // 把名字添加进去维护
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init(Class cla) {

    }
}
