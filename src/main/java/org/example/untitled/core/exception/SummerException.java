package org.example.untitled.core.exception;
// 全局异常类
public class SummerException extends RuntimeException {
    public SummerException(){
        super();
    }
    public SummerException(String message){
        super(message);
    }
}
