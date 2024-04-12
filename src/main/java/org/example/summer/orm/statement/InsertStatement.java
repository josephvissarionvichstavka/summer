package org.example.summer.orm.statement;

import org.example.summer.orm.ConnectionPool;
import org.example.summer.orm.resolver.Resolver;

import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertStatement implements  Statement{

    @Override
    public Object getResult(Resolver resolver, Parameter[] parameters, Object[] args) {
        Connection conn = ConnectionPool.getConnection(); // 返回连接
        PreparedStatement ps = null;
        int rs = 0;
        Map<String,Object[]> map = new HashMap<>();
        for(int i=0;i< parameters.length;i++){
            map.put(parameters[i].getName(),new Object[]{parameters[i].getType(),args[i]} );
        }
        try {
            ps = conn.prepareStatement(resolver.getSql());
            List<String> params = resolver.getParam();
            for(int i=1;i<=params.size();i++){
                StatementUtil.setValue(ps, (Class) map.get(params.get(i-1))[0],map.get(params.get(i-1))[1],i);
            }
            rs = ps.executeUpdate();
            ConnectionPool.releaseConnection(conn);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
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
