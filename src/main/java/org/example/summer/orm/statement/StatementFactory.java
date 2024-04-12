package org.example.summer.orm.statement;

import org.example.summer.orm.annotation.Insert;
import org.example.summer.orm.annotation.Select;

import java.lang.annotation.Annotation;

public class StatementFactory {
    public Statement create(Class<? extends Annotation> annotation){
        if (Insert.class.equals(annotation)){
            return new InsertStatement();
        }
        if (Select.class.equals(annotation)){
            return new SelectStatement();
        }
        return null;
    }
}
