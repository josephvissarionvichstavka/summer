package org.example.untitled.core.ioc.bean;

import org.example.untitled.core.ioc.contest.BeansContext;
import org.example.untitled.orm.annotation.MysqlConfig;

import java.util.List;

public class MysqlInit implements Init{
    @Override
    public void init(List<Class<?>> classes) {
        if (classes == null){
            return;
        }
        for (Class<?> aClass : classes) {
            MysqlConfig mysqlConfig = aClass.getAnnotation(MysqlConfig.class);
            try {
                BeansContext.add("MysqlConfig",aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init(Class cla) {

    }
}
