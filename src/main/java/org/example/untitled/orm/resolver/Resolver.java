package org.example.untitled.orm.resolver;

import java.util.List;

public class Resolver {
    private String sql; // sql语句
    private List<String> param; // sql参数列表

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<String> getParam() {
        return param;
    }

    public void setParam(List<String> param) {
        this.param = param;
    }
}
