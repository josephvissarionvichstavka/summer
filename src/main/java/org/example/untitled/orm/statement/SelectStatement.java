package org.example.untitled.orm.statement;

import org.example.untitled.orm.ConnectionPool;
import org.example.untitled.orm.resolver.Resolver;

import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectStatement implements Statement{
    @Override
    public Object getResult(Resolver resolver, Parameter[] parameters, Object[] args) {
        Connection conn = ConnectionPool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(resolver.getSql()); // 预编译sql
            rs = ps.executeQuery(); // 接返回值
            ResultSetMetaData md = rs.getMetaData(); // 返回的单条数据
            int columnSize = md.getColumnCount(); // 返回的大小
            List<Map> result = new ArrayList<>();
            while (rs.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= columnSize; i++) {
                    map.put(md.getColumnName(i), rs.getObject(i)); // 数据加入map
                }
                result.add(map);
            }
            ConnectionPool.releaseConnection(conn);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
