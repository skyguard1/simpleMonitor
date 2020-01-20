package com.skyguard.test;

import com.skyguard.test.transformer.TestMainTransformer;
import com.skyguard.test.transformer.TestTransformer;
import com.sun.tools.attach.VirtualMachine;

import java.lang.instrument.Instrumentation;

/**
 * @author : xingrufei
 * create at:  2019/12/19  2:00 下午
 * @description:
 */
public class AgentMain {


    public static void main(String[] args) {
        try {
            // 传入目标 JVM pid
            VirtualMachine vm = VirtualMachine.attach("8952");
            vm.loadAgent("/Users/xingrufei/IdeaProjects/simpleMonitor/target/simpleMonitor.jar");
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 该方法在main方法之前运行，与main方法运行在同一个JVM中
     * 并被同一个System ClassLoader装载
     * 被统一的安全策略(security policy)和上下文(context)管理
     */
    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("=========premain方法执行========");
        System.out.println(agentOps);
        // 添加Transformer
        inst.addTransformer(new TestMainTransformer());
    }

    /**
     * 如果不存在 premain(String agentOps, Instrumentation inst)
     * 则会执行 premain(String agentOps)
     */
    public static void premain(String agentOps) {
        System.out.println("=========premain方法执行2========");
        System.out.println(agentOps);
    }

    public static void agentmain(String args, Instrumentation inst) {
        //指定我们自己定义的Transformer，在其中利用Javassist做字节码替换
        inst.addTransformer(new TestTransformer(), true);
        try {
            //重定义类并载入新的字节码
            inst.retransformClasses(Monitor.class);
            System.out.println("Agent Load Done.");
        } catch (Exception e) {
            System.out.println("agent load failed!");
        }
    }



}