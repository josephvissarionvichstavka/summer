package org.example.untitled.orm.statement;

import org.example.untitled.orm.resolver.Resolver;

import java.lang.reflect.Parameter;

public interface Statement {
    Object getResult(Resolver resolver, Parameter[] parameters,Object[] args);
}
