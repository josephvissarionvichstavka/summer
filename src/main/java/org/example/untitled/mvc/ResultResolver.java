package org.example.untitled.mvc;

import javax.servlet.http.HttpServletResponse;

public interface ResultResolver {
    void resolver(HttpServletResponse response,Object result);
}
