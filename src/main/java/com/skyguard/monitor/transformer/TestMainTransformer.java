package com.skyguard.monitor.transformer;

import com.skyguard.monitor.annotation.Trace;
import com.skyguard.monitor.annotation.TraceMethod;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : xingrufei
 * create at:  2019/12/19  4:07 下午
 * @description:
 */
public class TestMainTransformer implements ClassFileTransformer {

    private final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
    private final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";

    // 被处理的方法列表
    private final static Map<String, List<String>> methodMap = new HashMap<String, List<String>>();

    public TestMainTransformer() {
        add("com.skyguard.monitor.MainTest.sayHello");
        add("com.skyguard.monitor.MainTest.sayHello2");
    }

    private void add(String methodString) {
        String className = methodString.substring(0, methodString.lastIndexOf("."));
        String methodName = methodString.substring(methodString.lastIndexOf(".") + 1);
        List<String> list = methodMap.get(className);
        if (list == null) {
            list = new ArrayList<String>();
            methodMap.put(className, list);
        }
        list.add(methodName);
    }

    public Map<String, List<String>> getMethodMap() {
        return methodMap;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        className = className.replace("/", ".");


        CtClass ctclass = null;
        try {
            ctclass = ClassPool.getDefault().get(className);// 使用全称,用于取得字节码类<使用javassist>
            if (ctclass.hasAnnotation(Trace.class)) {
                System.out.println("className:" + className);
                for (CtMethod m : ctclass.getMethods()) {
                    if (m.hasAnnotation(TraceMethod.class)) {
                        String methodName = m.getName();
                        System.out.println("methodName:" + methodName);
                        String outputStr = "\nSystem.out.println(\"this method " + methodName
                                + " cost:\" +(endTime - startTime) +\"ms.\");\n";

                        String testStr = "\nlong stime = endTime - startTime;\ncom.skyguard.monitor.trace.SimpleTracer.printMsg(\"" + className + "\",\"" + methodName + "\",stime);";

                        CtMethod ctmethod = ctclass.getDeclaredMethod(methodName);
                        String newMethodName = methodName + "$monitor";
                        ctmethod.setName(newMethodName);

                        // 创建新的方法，复制原来的方法，名字为原来的名字
                        CtMethod newMethod = CtNewMethod.copy(ctmethod, methodName, ctclass, null);

                        // 构建新的方法体
                        StringBuilder bodyStr = new StringBuilder();
                        bodyStr.append("{");
                        bodyStr.append(prefix);
                        bodyStr.append(newMethodName + "($$);\n");// 调用原有代码，类似于method();($$)表示所有的参数
                        bodyStr.append(postfix);
                        bodyStr.append(testStr);
                        bodyStr.append("}");

                        newMethod.setBody(bodyStr.toString());// 替换新方法
                        ctclass.addMethod(newMethod);// 增加新方法
                    }
                }
            }
            return ctclass.toBytecode();
        } catch (Throwable e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


}