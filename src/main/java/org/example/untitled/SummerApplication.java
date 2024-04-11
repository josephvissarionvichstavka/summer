package org.example.untitled;

import org.apache.catalina.LifecycleException;
import org.example.untitled.core.ioc.IocInit;
import org.example.untitled.core.server.SummerServer;
import org.example.untitled.orm.ConnectionPool;

public class SummerApplication {
    public static void run(Class main,Integer port) throws LifecycleException {
        IocInit.IocInit(main); // 启动ioc
        ConnectionPool.init(); // 启动连接池
        new SummerServer(port).run(); // 启动服务器
    }
    public static void run(Class main) throws LifecycleException {
        run(main,8080);// 默认8080
    }
}
