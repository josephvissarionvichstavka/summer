package org.example.untitled.core.ioc.bean;

import org.example.untitled.core.ioc.contest.BeansContext;
import org.example.untitled.core.ioc.contest.ParamContext;
import org.example.untitled.core.ioc.contest.Router;
import org.example.untitled.core.ioc.contest.RouterContext;
import org.example.untitled.mvc.annotation.Controller;
import org.example.untitled.mvc.annotation.GET;
import org.example.untitled.mvc.annotation.POST;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ControllerInit implements Init{
    @Override
    public void init(List<Class<?>> classes) {
        if (classes == null){
            return;
        }
        try {
            for (Class<?> aClass : classes) {
                Controller controller = aClass.getAnnotation(Controller.class);
                String controllerPath = controller.value().trim();
                Method[] declaredMethods = aClass.getDeclaredMethods();  //  获取controller类下全部方法
                for (Method method : declaredMethods) {
                    Annotation[] annotations = method.getAnnotations();  // 获取类的注解
                    ParamContext.addParam(aClass,method);
                    List<Class> classList = Arrays.asList(new Class[]{GET.class, POST.class
                            ,/*后续可以补充*/});
                    for (Class class_ : classList) {
                        Annotation annotation = method.getAnnotation(class_); // 方法上的注解关于classList
                        if (annotation != null){
                            Method value = class_.getMethod("value"); // 注解的value的方法
                            value.setAccessible(true); // value设置可访问
                            String path = controllerPath + value.invoke(annotation).toString();// 方法获取的全部路径
                            method.setAccessible(true); // 方法设置可以访问
                            Router router = new Router();
                            router.setUrl(path); // 设置path
                            router.setMethod(method); // 设置方法
                            router.setaClass(aClass); // 设置类
                            router.setReqMethodName(class_.getSimpleName()); // 设置访问方法
                            RouterContext.add(router);
                        }
                    }
                }
                BeansContext.add(aClass.getName(), aClass.newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(Class cla) {

    }
}
