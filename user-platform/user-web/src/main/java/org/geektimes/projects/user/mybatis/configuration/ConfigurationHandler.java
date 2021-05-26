package org.geektimes.projects.user.mybatis.configuration;

import org.apache.ibatis.session.Configuration;

/**
 * @description
 * @Author TheFool
 * @Date 2021/5/25 21:40
 */
@FunctionalInterface
public interface ConfigurationHandler {

    void handle(Configuration configuration);
}
