package com.skyguard.monitor.trace;

/**
 * @author : xingrufei
 * create at:  2020-01-20  16:56
 * @description:
 */
public interface Tracer {

    public void start();

    public void end();

    public void throwsException(String message);


}
