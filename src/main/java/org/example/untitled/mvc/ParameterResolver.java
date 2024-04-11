package org.example.untitled.mvc;

import javax.servlet.http.HttpServletRequest;
import org.example.untitled.core.ioc.contest.Router;

public interface ParameterResolver {
    Object[] resolver(HttpServletRequest request, Router router);
}
