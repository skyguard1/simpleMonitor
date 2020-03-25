package com.skyguard.monitor.agent.interceptor;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * @author : xingrufei
 * create at:  2020-03-25  16:28
 * @description:
 */
public class MethodInterceptor{

    public @RuntimeType
    Object intercept(@This Object obj,
                     @AllArguments Object[] allArguments,
                     @SuperCall Callable<?> zuper,
                     @Origin Method method
    ) throws Throwable {


        System.out.println("methodName:"+method.getName()+",params:"+ Arrays.toString(allArguments));

        return null;
    }



}
