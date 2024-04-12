package org.example.summer.mvc;

import javax.servlet.http.HttpServletRequest;
import org.example.summer.core.ioc.contest.BeansContext;
import org.example.summer.core.ioc.contest.Router;

import java.lang.reflect.InvocationTargetException;

public class Handler {
    protected Object start(HttpServletRequest request, Router router,Object[] args){
        Object bean = BeansContext.getBean(router.getaClass().getName()); // 将web类的实例抽出
        Object result  = null;
        try {
            result = router.getMethod().invoke(bean,args); // 执行方法的返回值
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
