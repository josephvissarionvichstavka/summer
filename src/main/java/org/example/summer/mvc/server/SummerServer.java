package org.example.summer.mvc.server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class SummerServer {
    private Integer port;

    public SummerServer(Integer port) {
        this.port = port;
    }

    private String getYuanShen(){
        return "原神启动";
    }
    public void run() throws LifecycleException {
        System.out.println(getYuanShen());
        Tomcat tomcat = new Tomcat();
        Connector connector = new Connector("HTTP/1.1");  // 协议
        connector.setPort(port);
        tomcat.getService().addConnector(connector);
        Context context = tomcat.addContext("/", null); // 添加上下文
        Tomcat.addServlet(context,"DispatcherServlet","org.example.untitled.mvc.DispatcherServlet");
        context.addServletMappingDecoded("/*","DispatcherServlet");// 添加servlet
        tomcat.start(); // 启动tomcat
        tomcat.getServer().await();// 开启异步
    }
}
