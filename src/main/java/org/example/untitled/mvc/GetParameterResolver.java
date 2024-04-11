package org.example.untitled.mvc;

import javax.servlet.http.HttpServletRequest;
import org.example.untitled.core.exception.SummerException;
import org.example.untitled.core.ioc.contest.ParamContext;
import org.example.untitled.core.ioc.contest.Router;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetParameterResolver implements ParameterResolver{
    @Override
    public Object[] resolver(HttpServletRequest request, Router router) {
        String[] param = ParamContext.getParamByClass(router.getaClass(), router.getMethod());// 该方法的全部参数
        List<Object> value = new ArrayList<>();
        Map<String, String[]> parameterMap = request.getParameterMap(); // 返回体中的全部参数
        for (String name : param) {
            if (null == parameterMap.get(name)){ // 参数需要一一对应
                throw new SummerException("参数异常，uri参数异常");
            }
            value.add(parameterMap.get(name)[0]);
        }
        return value.toArray();
    }
}
