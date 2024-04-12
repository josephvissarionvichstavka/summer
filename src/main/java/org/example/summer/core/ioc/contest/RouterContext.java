package org.example.summer.core.ioc.contest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RouterContext { // web实例管理器
    private static Map<String,Map<String,Router>> routers = new ConcurrentHashMap<>();

    public static void add(Router router){
        if (router != null){
            String url = router.getUrl(); // router实例的网络路径
            Map<String, Router> stringRouterMap = routers.get(router.getReqMethodName()); // 返回网络方法的全部kv
            if (stringRouterMap == null){// 如果该网络方法里面为空
                stringRouterMap = new ConcurrentHashMap<>();
            }
            stringRouterMap.put(url,router);
            routers.put(router.getReqMethodName(),stringRouterMap);
        }
    }
    public static Map<String,Map<String,Router>> getAll(){
        return routers;
    }

    public static Map<String,Router> getRouter(String method){
        return routers.get(method);
    }
}
