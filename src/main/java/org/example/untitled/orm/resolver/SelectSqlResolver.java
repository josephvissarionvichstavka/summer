package org.example.untitled.orm.resolver;

import org.example.untitled.core.exception.SummerException;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectSqlResolver extends SqlAbstractResolver{
    @Override
    public Resolver resolver(String sqlValue, Parameter[] parameters, Object[] args) {
        Map map = new HashMap();
        for(int i=0;i< parameters.length;i++){
            map.put(parameters[i].getName(), args[i]);
        } // 将变量与别名一一对应
        StringBuilder builder = new StringBuilder();
        List<String> params = new ArrayList();
        for(int i=0;i<sqlValue.length();i++){ // 遍历sql
            char c = sqlValue.charAt(i);
            if(c=='#'){
                if(sqlValue.charAt(i+1)!='{'){
                    throw new SummerException("sql异常，请检查"+sqlValue);
                }
                StringBuilder p  = new StringBuilder();
                if(sqlValue.charAt(i+2)=='%'){
                    i = validationEnd(p,i+1,sqlValue);
                    String param = map.get(p.toString()).toString(); // 返回别名的实际值
                    params.add(p.toString());
                    if(sqlValue.charAt(i-1)=='%'){
                        builder.append("'%").append(param).append("%'");
                    }else {
                        builder.append("'%").append(param).append("'");
                    } // 重组sql
                }else {
                    i = validationEnd(p,i,sqlValue);
                    String param = map.get(p.toString()).toString();
                    params.add(p.toString());
                    if(sqlValue.charAt(i-1)=='%'){
                        builder.append("'").append(param).append("%'");
                    }else {
                        builder.append("'").append(param).append("'");
                    }
                }
                continue;
            }
            builder.append(c);
        }
        Resolver resolver = new Resolver();
        resolver.setSql(builder.toString());
        resolver.setParam(params);
        return resolver;
    }
}
