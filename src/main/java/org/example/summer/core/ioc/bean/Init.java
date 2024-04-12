package org.example.summer.core.ioc.bean;

import java.util.List;

public interface Init {
    void init(List<Class<?>> classes);

    void init(Class cla);
}
