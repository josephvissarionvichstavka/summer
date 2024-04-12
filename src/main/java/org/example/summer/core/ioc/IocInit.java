package org.example.summer.core.ioc;

import java.util.HashSet;
import java.util.Set;

public class IocInit {
    public synchronized static void IocInit(Class main){
        Set<String> scanPackage = new HashSet<>();
        scanPackage.add(main.getPackage().getName()); // 找到主类名下的包
        Initialize.init(scanPackage); // 装填
        Initialize.interFlow(); // 依赖注入
    }
}
