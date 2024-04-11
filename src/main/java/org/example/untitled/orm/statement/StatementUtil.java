package org.example.untitled.orm.statement;

import org.example.untitled.orm.annotation.Select;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementUtil {
    public static void setValue(PreparedStatement preparedStatement,Class aClass,Object value ,int i){
        try { // 对方法值替换
            if (String.class.equals(aClass)){
                preparedStatement.setString(i, (String) value);
            }else if (Integer.class.equals(aClass)){
                preparedStatement.setInt(i, (Integer) value);
            }else if (Date.class.equals(aClass)){
                preparedStatement.setDate(i, (Date) value);
            }else if (int.class.equals(aClass)){
                preparedStatement.setInt(i, (Integer) value);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
