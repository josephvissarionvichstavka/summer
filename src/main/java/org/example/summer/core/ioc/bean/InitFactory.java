package org.example.summer.core.ioc.bean;

import org.example.summer.core.ioc.annotation.Bean;
import org.example.summer.mvc.annotation.Controller;
import org.example.summer.orm.annotation.Mapper;
import org.example.summer.orm.annotation.MysqlConfig;

import java.lang.annotation.Annotation;

public class InitFactory { // 工厂类
    public Init init(Class<? extends Annotation> annotation){
        if (Bean.class.equals(annotation)){
            return new BeanInit();
        }
        if (Controller.class.equals(annotation)){
            return new ControllerInit();
        }
        if (Mapper.class.equals(annotation)){
            return new MapperInit();
        }
        if (MysqlConfig.class.equals(annotation)){
            return new MysqlInit();
        }
        return null;
    }
}
