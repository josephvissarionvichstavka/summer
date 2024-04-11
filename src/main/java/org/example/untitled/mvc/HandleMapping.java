package org.example.untitled.mvc;

import javax.servlet.http.HttpServletRequest;
import org.example.untitled.core.ioc.contest.Router;
import org.example.untitled.core.ioc.contest.RouterContext;

import java.util.Map;

public class HandleMapping {
    public Router mapping(HttpServletRequest request){
        String uri = request.getRequestURI();
        System.out.println("开始匹配" + uri);
        Map<String, Router> routers = RouterContext.getRouter(request.getMethod()); // 返回该网络方法的全部
        Router router = routers.get(uri); // 找到该方法
        System.out.println("匹配uri{}成功" + uri);
        return router;
    }
}
