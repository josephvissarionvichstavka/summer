package org.example.summer.core.ioc.bean;

import org.example.summer.core.ioc.contest.BeansContext;
import org.example.summer.orm.annotation.MysqlConfig;

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
