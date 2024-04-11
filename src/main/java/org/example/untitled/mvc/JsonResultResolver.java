package org.example.untitled.mvc;

import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletResponse;
import org.example.untitled.core.util.R;

import java.io.IOException;
import java.io.PrintWriter;

public class JsonResultResolver implements ResultResolver{
    @Override
    public void resolver(HttpServletResponse response, Object result) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter(); // 写入返回体
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(R.success(result)); // 返回结果转json格式
        printWriter.write(jsonObject.toJSONString()); // 发送json
    }
}
