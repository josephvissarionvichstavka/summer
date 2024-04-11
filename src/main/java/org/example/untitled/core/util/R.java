package org.example.untitled.core.util;

import java.util.HashMap;

public class R extends HashMap<String,Object> {
    public static final long serialVersionUID = 1L;

    public R(){
        put("code",0);
        put("msg","success");
    }
    public static R error(int code,String msg){
        R r = new R();
        r.put("code",code);
        r.put("msg",msg);
        return r;
    }
    public static R success(){
        return new R();
    }
    public static R success(Object data){
        R r = new R();
        r.put("data",data);
        return r;
    }
    public static R success(String msg){
        R r = new R();
        r.put("msg",msg);
        return r;
    }
    public static R success(String msg,Object data){
        R r = new R();
        r.put("msg",msg);
        r.put("data",data);
        return r;
    }
}
