package com.skyguard.test.trace;

/**
 * @author : xingrufei
 * create at:  2020-01-13  16:17
 * @description:
 */
public class SimpleTracer {

    public static void printMsg(String className,String methodName,long time){
        System.out.println("className:"+className+",method:"+methodName+",time:"+time);

    }



}
