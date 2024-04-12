package org.example.summer.orm;

import org.example.summer.core.exception.SummerException;
import org.example.summer.core.ioc.contest.BeansContext;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    public static String driverName = null; // 启动类
    public static String url = null; // url
    public static String username = null; // 用户名
    public static String password = null; // 密码
 //   public static String name = null; // 库名

    public static int maxConnections = 50; // 最大连接数
    public static int initConnections = 5; // 初始化连接数
    public static int maxActiveConnections = 100; // 最大等待队列

    private static List<Connection> freeConnection = new Vector<Connection>(); // 空闲连接池
    private static List<Connection> activeConnection = new Vector<Connection>(); // 连接池
    private static AtomicInteger countConne = new AtomicInteger(0); // 当前活动

    private static Connection newConnection(){
        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection; // 连接
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public static synchronized Connection getConnection(){
        try {
            Connection connection = null;
            if (countConne.get() < maxActiveConnections){
                if (freeConnection.size() > 0){
                    connection = freeConnection.remove(0); // 取出一个
                }else {
                    connection = newConnection(); // 新整一个
                }
                if (connection != null && !connection.isClosed()){
                    activeConnection.add(connection); // 添加到工作池
                    countConne.getAndIncrement(); // 确保原子形
                }else {
                    connection = getConnection(); // 重新获取
                }
            }else {
                connection = getConnection();
            }
            return connection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public static void releaseConnection(Connection connection){
        try {
            if (connection != null && !connection.isClosed()){
                if (freeConnection.size() < maxConnections){
                    freeConnection.add(connection); // 空闲队列+1
                }else {
                    connection.close(); // 关闭连接
                }
                activeConnection.remove(connection); // 移除工作队列
                countConne.getAndIncrement();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private static void AddOneConnectToFree(){
        Connection connection = newConnection();
        if (connection != null){
            freeConnection.add(connection);// 添加新连接
        }else {
            throw new SummerException("数据库连接失败");
        }
    }
    public static void init(){
        try {
            Object mysqlConfig = BeansContext.getBean("MysqlConfig"); // 返回mysqlconfig实例
            Class<?> aClass = mysqlConfig.getClass();
            driverName = aClass.getMethod("getDriverName").invoke(mysqlConfig).toString();
            url = aClass.getMethod("getUrl").invoke(mysqlConfig).toString();
            username = aClass.getMethod("getUsername").invoke(mysqlConfig).toString();
            password = aClass.getMethod("getPassword").invoke(mysqlConfig).toString();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        if (initConnections > 0){
            for (int i = 0; i < initConnections; i++) {
                AddOneConnectToFree(); // 初始化连接池
            }
        }
    }
}
