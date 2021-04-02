package org.geektimes.initializer;

import org.geektimes.context.ClassicComponentContext;
import org.geektimes.context.ComponentContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @description
 * @Author TheFool
 * @Date 2021/3/24 23:02
 */
public class ComponentContextInitializer implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        ClassicComponentContext componentContext = new ClassicComponentContext();
        componentContext.init(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComponentContext context = ClassicComponentContext.getInstance();
        context.destroy();
    }
}
