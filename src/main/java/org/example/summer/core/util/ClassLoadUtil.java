package org.example.summer.core.util;

import javassist.ClassPool;
import javassist.Loader;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassLoadUtil {
    private static ClassLoader classLoader;

    private static void findAndLoadClassInPackageByFile(String packageName, String packagePath, List<Class<?>> classes){
        File dir = new File(packagePath); // 获取包路径
        if (!dir.exists() || !dir.isDirectory()){
            return;
        }
        //  获取包路径下所有的class文件
        File[] files = dir.listFiles(file -> (file.isDirectory() || file.getName().endsWith(".class")));

        assert files != null;
        for (File file : files) {
            if (file.isDirectory()){ // 是文件夹就递归
                findAndLoadClassInPackageByFile(packageName + "." + file.getName(),file.getAbsolutePath(),classes);
            }else {
                String className = file.getName().substring(0, file.getName().length() - 6);// 去掉 .class
                try {
                    classes.add(classLoader.loadClass(packageName+"."+className));// 找到就添加进去
                }catch (Throwable e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public static List<Class<?>> loadClass(final String packageName,boolean isJavassist){// 包名  是否是字节码文件
        List<Class<?>> classes = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');// 方便查询文件
        try {
            if (isJavassist){
                ClassPool pool = ClassPool.getDefault();
                Loader loader = new Loader(pool);
                loader.delegateLoadingOf("jdk.internal.reflect.");
                classLoader = loader;
            }else {
                classLoader = Thread.currentThread().getContextClassLoader(); // 上下文类加载器
            }
            Enumeration<URL> dirs = classLoader.getResources(packageDirName);  //  遍历包中所有路径
            while (dirs.hasMoreElements()){   //  下一个url
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();  //  url的协议类型
                if ("file".equals(protocol)){  // 文件类型
                    String filePath = URLDecoder.decode(url.getFile(),"UTF-8");
                    findAndLoadClassInPackageByFile(packageName,filePath,classes);  // 把文件加进去
                }else if ("jar".equals(protocol)){  // 压缩包类型
                    JarFile jar;
                    try {
                        String swap_name;  // 中间变量
                        jar = ((JarURLConnection)url.openConnection()).getJarFile();  // 返回jar的数据
                        Enumeration<JarEntry> entries = jar.entries();  // jar中所有的文件
                        while (entries.hasMoreElements()){
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName(); // 文件的名字
                            if (name.charAt(0) == '/'){   // 去掉开头的/
                                name = name.substring(1);
                            }
                            if (name.startsWith(packageDirName)){ // 如果文件开头的名字是包名就添加
                                int index = name.lastIndexOf('/');
                                if (index == -1){ // 如果查到后面没有目录，就是文件名
                                    swap_name = packageName;
                                }else {
                                    swap_name = name.substring(0,index).replace('/','.'); // 把包名换回来
                                }
                                if (name.endsWith(".class") && !entry.isDirectory()){ // 末尾是.class 并且不是包
                                    String className = name.substring(swap_name.length() + 1, name.length() - 6);
                                    try {// 把文件添加进去
                                        classes.add(classLoader.loadClass(swap_name + '.' + className));
                                    } catch (ClassNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
