package com.skyguard.monitor.agent.matcher;

import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author : xingrufei
 * create at:  2020-03-26  14:37
 * @description:
 */
public class ClassMatcher<T> extends ElementMatcher.Junction.AbstractBase<T>{

    private final ElementMatcher<? super T> matcher;

    public ClassMatcher(ElementMatcher<? super T> matcher){
        this.matcher = matcher;
    }

    @Override
    public boolean matches(T target) {
        try {
            return this.matcher.matches(target);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }
}
