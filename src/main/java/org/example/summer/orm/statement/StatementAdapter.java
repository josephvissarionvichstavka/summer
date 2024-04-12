package org.example.summer.orm.statement;

import org.example.summer.orm.resolver.Resolver;
import org.example.summer.orm.resolver.ResolverFactory;
import org.example.summer.orm.resolver.SqlAbstractResolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class StatementAdapter {
    public static Object statement(String sqlValue, Parameter[] parameters, Object[] args, Class<? extends Annotation> annotation){
        ResolverFactory resolverFactory = new ResolverFactory();
        SqlAbstractResolver sqlAbstractResolver = resolverFactory.create(annotation);
        Resolver resolver = sqlAbstractResolver.resolver(sqlValue, parameters, args);
        StatementFactory statementFactory = new StatementFactory();
        Statement statement = statementFactory.create(annotation);
        Object result = statement.getResult(resolver, parameters, args);
        return result;
    }
}
