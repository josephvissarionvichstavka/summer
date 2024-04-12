package org.example.summer.mvc;

import javax.servlet.http.HttpServletRequest;
import org.example.summer.core.ioc.contest.Router;

public interface ParameterResolver {
    Object[] resolver(HttpServletRequest request, Router router);
}
