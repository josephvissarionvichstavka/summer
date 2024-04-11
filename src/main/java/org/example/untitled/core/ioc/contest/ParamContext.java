package org.example.untitled.core.ioc.contest;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class ParamContext {// 类中局部变量管理器
    private static final ConcurrentHashMap<Class,ConcurrentHashMap<Method,String[]>> paramMap =
            new ConcurrentHashMap<>();

    public static String[] getParamByClass(Class aClass,Method method){
        return paramMap.get(aClass).get(method);
    }// 获取方法中参数数组

    private static String[] getParamNames(Method method){
        try {
            Class<?> aClass = method.getDeclaringClass(); // 声明该方法的类
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.get(aClass.getName());
            ctClass.freeze(); // 冻结类
            ctClass.defrost(); // 解冻类
            CtClass[] params = new CtClass[method.getParameterTypes().length]; //创建一个CtClass数组长度为方法的参数个数
            for (int i = 0;i < method.getParameterTypes().length ;++i){
                ClassClassPath classClassPath = new ClassClassPath(method.getParameterTypes()[i]);
                // classclasspath储存参数的类路径
                classPool.insertClassPath(classClassPath);
                params[i] = classPool.getCtClass(method.getParameterTypes()[i].getName());// param储存参数名称
            }
            CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName(), params);// 返回该方法
            MethodInfo methodInfo = ctMethod.getMethodInfo(); // 方法的信息
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute(); // 方法的代码属性
            LocalVariableAttribute attribute = // 方法的局部变量
                    (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1; // 判断方法是否是静态方法
            String[] paramNames = new String[ctMethod.getParameterTypes().length]; // 方法的参数名称的数组
            for (int i = 0; i < attribute.tableLength(); ++i){ // 遍历所有局部变量
                if (attribute.index(i) >= pos//是否是在参数范围
                        && attribute.index(i) < paramNames.length + pos){
                    paramNames[attribute.index(i) - pos] = attribute.variableName(i); // 储存当前索引的变量名
                }
            }
            return paramNames; // 返回
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new String[]{};
        }
    }

    public static void addParam(Class aClass,Method method) throws Exception {
        String[] paramNames = getParamNames(method);
        if (method.getParameterTypes().length == paramNames.length){ // 如果当前方法的参数个数与类中该方法的参数列表相等
            if (paramMap.contains(aClass)){ // map里面是否有这个类
                ConcurrentHashMap<Method,String[]> concurrentHashMap = paramMap.get(aClass); // 返回类的方法和参数的集合
                concurrentHashMap.put(method,paramNames);  // 把方法和参数列表添加进去
                paramMap.put(aClass,concurrentHashMap);
            }else {
                ConcurrentHashMap<Method,String[]> concurrentHashMap = new ConcurrentHashMap<>();
                concurrentHashMap.put(method,paramNames);
                paramMap.put(aClass,concurrentHashMap);
            }
        }else {
            throw new Exception("参数异常");
        }
    }
}
