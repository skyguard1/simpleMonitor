package com.skyguard.monitor.trace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : xingrufei
 * create at:  2020-01-20  17:22
 * @description:
 */
public class Monitor {

    private static final Logger LOG = LoggerFactory.getLogger(Monitor.class);

    private static ThreadLocal<CallTracer> threadLocal = new InheritableThreadLocal<>();

    private static ReentrantLock lock = new ReentrantLock();

    public static void start(MethodInfo methodInfo){

        try{
            lock.lock();
            CallTracer tracer = threadLocal.get();
            if(tracer==null){
                tracer = new CallTracer(methodInfo);
            }

            tracer.start();

        }catch (Exception e){
            LOG.error("set methodInfo error",e);
        }finally {
            lock.unlock();
        }


    }

    public static void exception(Throwable throwable){

        try{
            lock.lock();

            CallTracer tracer = threadLocal.get();
            if(tracer==null){
                throw new RuntimeException("monitor execute error");
            }

            if(throwable!=null) {
                tracer.throwsException(throwable.getMessage());
            }

        }catch (Exception e){
            LOG.error("monitor error",e);
        }finally {
            lock.unlock();
            threadLocal.remove();
        }


    }

    public static void end(String result){

        try{
            lock.lock();
            CallTracer tracer = threadLocal.get();
            if(tracer==null){
                throw new RuntimeException("monitor execute error");
            }

            tracer.end(result);

        }catch (Exception e){
            LOG.error("set methodInfo error",e);
        }finally {
            lock.unlock();
            threadLocal.remove();
        }


    }





}
