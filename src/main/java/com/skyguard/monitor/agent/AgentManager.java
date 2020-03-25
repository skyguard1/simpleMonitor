package com.skyguard.monitor.agent;

import com.skyguard.monitor.agent.listener.AgentListener;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.nameContains;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

/**
 * @author : xingrufei
 * create at:  2020-03-25  16:13
 * @description:
 */
public class AgentManager {

    public void runAgent(Instrumentation instrumentation){

        AgentClassManager manager = new AgentClassManager();

        new AgentBuilder.Default()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .with(new AgentListener())
                .disableClassFormatChanges()
                .ignore(
                        nameStartsWith("net.bytebuddy")
                                .or(nameStartsWith("org.slf4j"))
                                .or(nameStartsWith("org.apache.logging"))
                                .or(nameStartsWith("org.groovy"))
                                .or(nameContains("javassist"))
                                .or(nameStartsWith("sun"))
                                .or(nameContains("asm"))
                                .or(nameStartsWith("com.skyguard.monitor"))
                                .or(nameStartsWith("com.intellij"))
                                .or(nameStartsWith("java"))
                                .or(nameStartsWith("jdk"))
                                .or(ElementMatchers.<TypeDescription>isSynthetic()))
                .type(getTypes())
                .transform(manager)
                .installOn(instrumentation);

    }


    private ElementMatcher.Junction<TypeDescription> getTypes() {
        ElementMatcher.Junction junction = ElementMatchers.<TypeDescription>any();

        return junction;
    }


}
