package org.example.untitled.orm.statement;

import org.example.untitled.orm.annotation.Insert;
import org.example.untitled.orm.annotation.Select;

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
