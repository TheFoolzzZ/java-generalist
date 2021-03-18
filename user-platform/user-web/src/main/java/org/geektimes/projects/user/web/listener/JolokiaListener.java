package org.geektimes.projects.user.web.listener;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.management.UserManager;
import org.geektimes.projects.user.sql.DBConnectionManager;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;
import java.util.logging.Logger;

public class JolokiaListener implements ServletContextListener {

    private final Logger logger = Logger.getLogger(DBConnectionManager.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // 获取平台 MBean Server
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        // 为 UserMXBean 定义 ObjectName
        ObjectName objectName = null;
        try {
            objectName = new ObjectName("jolokia:type=User");
            // 创建 UserMBean 实例
            User user = new User();
            mBeanServer.registerMBean(new UserManager(user),objectName);
        } catch (Exception e) {
            logger.warning(String.format("JolokiaListener#contextInitialized.objectName:%s,error:%s, heapStack:%s",objectName,e.getMessage(),e));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
