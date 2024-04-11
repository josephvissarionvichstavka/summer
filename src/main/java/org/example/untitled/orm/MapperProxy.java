package org.example.untitled.orm;

import org.example.untitled.orm.annotation.Delete;
import org.example.untitled.orm.annotation.Insert;
import org.example.untitled.orm.annotation.Select;
import org.example.untitled.orm.annotation.Update;
import org.example.untitled.orm.statement.StatementAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MapperProxy implements InvocationHandler { // sql注解代理
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Parameter[] parameters = method.getParameters();
        Select select = method.getAnnotation(Select.class);
        Insert insert = method.getAnnotation(Insert.class);
        Update update = method.getAnnotation(Update.class);
        Delete delete = method.getAnnotation(Delete.class);
        /*后续可以添加*/
        String sql = null;
        if (select != null){
            sql = select.value();
            return StatementAdapter.statement(sql,parameters,args,Select.class);
        }
        if (insert != null){
            sql = insert.value();
            return StatementAdapter.statement(sql,parameters,args,Insert.class);
        }
        if (update != null){
            sql = update.value();
            return StatementAdapter.statement(sql,parameters,args,Update.class);
        }
        if (delete != null){
            sql = delete.value();
            return StatementAdapter.statement(sql,parameters,args,Delete.class);
        }
        return null;
    }
}
