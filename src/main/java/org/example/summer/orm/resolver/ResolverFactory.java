package org.example.summer.orm.resolver;

import org.example.summer.orm.annotation.Insert;
import org.example.summer.orm.annotation.Select;

import java.lang.annotation.Annotation;

public class ResolverFactory {
    public SqlAbstractResolver create(Class<? extends Annotation> annotation){
        if (Insert.class.equals(annotation)){
            return new InsertSqlResolver();
        }
        if (Select.class.equals(annotation)){
            return new SelectSqlResolver();
        }
        return null;
    }
}
