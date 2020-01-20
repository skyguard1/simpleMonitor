package com.skyguard.test.transformer;

import com.skyguard.test.annotation.Trace;
import com.skyguard.test.annotation.TraceMethod;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;

/**
 * @author : xingrufei
 * create at:  2019/12/19  2:01 下午
 * @description:
 */
public class TestTransformer implements ClassFileTransformer {

    private final static String prefix = "\nlong startTime = System.currentTimeMillis();\n";
    private final static String postfix = "\nlong endTime = System.currentTimeMillis();\n";


    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("Transforming " + className);
        try {
            String className1 = className.replace("/", ".");
            if (classBeingRedefined.isAnnotationPresent(Trace.class)) {
                ClassPool cp = ClassPool.getDefault();
                CtClass cc = cp.get(className1);
                for(CtMethod m:cc.getMethods()) {
                    if(m.hasAnnotation(TraceMethod.class)) {
                        MethodInfo methodInfo = m.getMethodInfo();
                        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
                        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                                .getAttribute(LocalVariableAttribute.tag);
                        int pos = Modifier.isStatic(m.getModifiers()) ? 0 : 1;
                        String[] paramNames = new String[m.getParameterTypes().length];
                        for (int i = 0; i < paramNames.length; i++) {
                            paramNames[i] = attr.variableName(i + pos);
                        }
                        String params = "";
                        for (int i = 0; i < paramNames.length; i++) {
                            params += paramNames[i];
                            if (i != paramNames.length - 1) {
                                params += ",";
                            }
                        }
                        m.instrument(new ExprEditor() {
                            public void edit(MethodCall m) throws CannotCompileException {
                                m.replace("{ long stime = System.currentTimeMillis(); $_ = $proceed($$);System.out.println(\""
                                        + m.getClassName() + "." + m.getMethodName()
                                        + " cost:\" + (System.currentTimeMillis() - stime) + \" ms\");}");
                            }
                        });

                   /* CtClass ctClass = m.getReturnType();
                        m.insertBefore("{ System.out.println(\"start,params is:" + params + ",return type is :" + ctClass.getName() + "\"); }");
                        m.insertAfter("{ System.out.println(\"end\"); }");*/
                    }
                }
                return cc.toBytecode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}