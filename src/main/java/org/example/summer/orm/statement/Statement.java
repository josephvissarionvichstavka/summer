package org.example.summer.orm.statement;

import org.example.summer.orm.resolver.Resolver;

import java.lang.reflect.Parameter;

public interface Statement {
    Object getResult(Resolver resolver, Parameter[] parameters,Object[] args);
}
