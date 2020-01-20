package com.skyguard.test;

import com.skyguard.test.annotation.Trace;
import com.skyguard.test.annotation.TraceMethod;

import java.lang.management.ManagementFactory;

/**
 * @author : xingrufei
 * create at:  2019/12/19  2:07 下午
 * @description:
 */
@Trace
public class Monitor {


    public static void main(String[] args) {

        String name = ManagementFactory.getRuntimeMXBean().getName();
        String s = name.split("@")[0];
        //打印当前Pid
        System.out.println("pid:"+s);

        for(int i=0;i<100;i++){
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            process(i);
        }





    }


    @TraceMethod
    public static void process(int i){
        try {
            Thread.sleep(1000L);
            test();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("process "+i);
    }

    public static void test(){
        System.out.println("test123");
    }


}