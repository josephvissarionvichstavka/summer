package org.example.summer.mvc;

import org.example.summer.core.ioc.contest.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dispatcher {
    HttpServletRequest request;
    HttpServletResponse response;

    public Dispatcher(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void analysis(){
        HandleMapping handleMapping = new HandleMapping();
        Router router = handleMapping.mapping(request); // 该方法的信息router
        ParameterResolver pr = null;
        if ("GET".equals(request.getMethod())){ // 如果网络方法是get
            pr  = new GetParameterResolver();
        }else if ("POST".equals(request.getMethod())){
            pr = new PostParameterResolver();
        }
        Object[] args = pr.resolver(request, router); // 参数列表
        Handler handler = new Handler();
        Object result = handler.start(request, router, args); // 方法的返回列表
        JsonResultResolver jsonResultResolver = new JsonResultResolver();
        jsonResultResolver.resolver(response,result); // 发送
    }
}
