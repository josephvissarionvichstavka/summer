package org.example.summer.orm.resolver;

import org.example.summer.core.exception.SummerException;

import java.lang.reflect.Parameter;

public abstract class SqlAbstractResolver {
    public abstract Resolver resolver(String sqlValue, Parameter[] parameters,Object[] args);

    public int validationEnd(StringBuilder stringBuilder,int i,String sqlValue){
        int end = 0;
        for (int j = i + 2;j < sqlValue.length(); ++ j){
            if (sqlValue.charAt(j) == '%'){
                if (sqlValue.charAt(j+1) == '}'){
                    end = j+1;
                }else {
                    throw new SummerException("sql语句错误");
                }
                break;
            }
            if (sqlValue.charAt(j) == '}'){
                end = j;
                break;
            }
            stringBuilder.append(sqlValue.charAt(j));
        }
        return end;
    }
}
