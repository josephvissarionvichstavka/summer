package org.example.summer.mvc;



import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.example.summer.core.exception.SummerException;
import org.example.summer.core.ioc.contest.Router;
import org.example.summer.mvc.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostParameterResolver implements ParameterResolver{
    @Override
    public Object[] resolver(HttpServletRequest request, Router router) {
        List list = new ArrayList();
        Map<String,Object> params = new HashMap<>();
        BufferedReader bufferedReader;
        try {
            bufferedReader = request.getReader(); // 写返回体
            String str ,wholeStr = "";
            while (null != (str = bufferedReader.readLine())){
                wholeStr += str;
            } // 将返回体中信息全部写进wholeStr
            if (StringUtils.isNotEmpty(wholeStr)){
                params = JSON.parseObject(wholeStr,Map.class);
            } // 转换成json格式
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String[]> parameterMap = request.getParameterMap(); // 参数列表
        for (Parameter parameter : router.getMethod().getParameters()) {
            if (parameter.getAnnotation(RequestBody.class) != null){
                list.add(params); // 不是返回体直接添加参数
            }else {
                if (parameterMap.get(parameter.getName()) == null){ // 参数需要一一对应
                    throw new SummerException("参数异常，uri参数异常");
                }
                list.add(parameterMap.get(parameter.getName())[0]);
            }
        }
        return list.toArray();
    }
}
