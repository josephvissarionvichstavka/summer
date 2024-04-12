package org.example.summer.orm.resolver;

import org.example.summer.core.exception.SummerException;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertSqlResolver extends SqlAbstractResolver{
    @Override
    public Resolver resolver(String sqlValue, Parameter[] parameters, Object[] args) {
        Map map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        List<String> params = new ArrayList<>();
        for (int i = 0; i < sqlValue.length(); i++) {
            char c = sqlValue.charAt(i);
            if (c == '#'){ // #{xxx}
                if (sqlValue.charAt(i + 1) != '{'){
                    throw new SummerException("SQL语句错误");
                }
                StringBuilder p = new StringBuilder();

                i = validationEnd(p,i,sqlValue);
                params.add(p.toString());
                builder.append("?");

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
