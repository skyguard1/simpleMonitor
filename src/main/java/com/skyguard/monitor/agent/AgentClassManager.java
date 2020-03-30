package com.skyguard.monitor.agent;

import com.skyguard.monitor.agent.interceptor.AgentInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : xingrufei
 * create at:  2020-03-25  16:15
 * @description:
 */
public class AgentClassManager implements AgentBuilder.Transformer {


    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
        return builder.visit(Advice
                .to(AgentInterceptor.class)
                .on(ElementMatchers.isAnnotatedWith(RequestMapping.class).or(ElementMatchers.isAnnotatedWith(GetMapping.class)).or(ElementMatchers.isAnnotatedWith(PostMapping.class)).or(ElementMatchers.isAnnotatedWith(PutMapping.class)))
        );
    }
}
