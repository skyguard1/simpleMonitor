package com.skyguard.monitor.trace;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : xingrufei
 * create at:  2020-01-20  16:59
 * @description:
 */
public class LongCounter implements Counter {

    private final AtomicLong counter = new AtomicLong();

    @Override
    public void add() {
       counter.getAndAdd(1L);
    }



}
