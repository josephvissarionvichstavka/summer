package org.example.untitled.core.ioc.contest;

import java.lang.reflect.Method;

public class Router {

    private String url;
    private Method method;
    private String reqMethodName;
    private Class<?> aClass;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getReqMethodName() {
        return reqMethodName;
    }

    public void setReqMethodName(String reqMethodName) {
        this.reqMethodName = reqMethodName;
    }

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
    }
}
