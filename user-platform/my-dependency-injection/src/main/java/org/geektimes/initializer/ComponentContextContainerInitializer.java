package org.geektimes.initializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * @description
 * @Author chengde.tan
 * @Date 2021/3/24 23:32
 */
public class ComponentContextContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        ctx.addListener(ComponentContextInitializer.class);
    }
}
