package com.skyguard.monitor.agent.interceptor;

import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author : xingrufei
 * create at:  2020-03-25  16:24
 * @description:
 */
public class AgentInterceptor {

    public static long time;

    @Advice.OnMethodEnter
    public static void enter(@Advice.This Object obj,@Advice.Origin Method originMethod, @Advice.AllArguments Object[] arguments) {
        try {
            time = System.currentTimeMillis();
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass("com.skyguard.monitor.agent.interceptor.MethodInterceptor");
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("intercept")) {
                    method.invoke(clazz.newInstance(),obj,arguments,null,originMethod);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method, @Advice.AllArguments Object[] arguments) {
            System.out.println("Exit " + method.getName() + " with arguments: " + Arrays.toString(arguments)+"times:"+(System.currentTimeMillis()-time)+"ms");

    }



}
