/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geektimes.projects.user.mybatis.annotation;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

/**
 * TODO Comment
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 *
 * Date : 2021-05-06
 */
public class MyBatisBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware, ResourceLoaderAware {

    private Environment environment;
    private ResourceLoader resourceLoader;

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = genericBeanDefinition(SqlSessionFactoryBean.class);

        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableMyBatis.class.getName());
        /**
         *  <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
         *   <property name="dataSource" ref="dataSource" />
         *   <property name="mapperLocations" value="classpath*:" />
         *  </bean >
         */
        beanDefinitionBuilder.addPropertyReference("dataSource", (String) attributes.get("dataSource"));
        // Spring String 类型可以自动转化 Spring Resource
        beanDefinitionBuilder.addPropertyValue("configLocation", attributes.get("configLocation"));
        beanDefinitionBuilder.addPropertyValue("mapperLocations", attributes.get("mapperLocations"));
        beanDefinitionBuilder.addPropertyValue("environment", resolvePlaceholder(attributes.get("environment")));
        // 自行添加其他属性
        beanDefinitionBuilder.addPropertyValue("plugins", resolveObjects(attributes.get("plugins")));
        beanDefinitionBuilder.addPropertyValue("typeHandlers", resolveTypeHandlers(attributes.get("typeHandlers")));
        beanDefinitionBuilder.addPropertyValue("typeAliases", resolveObjects(attributes.get("typeAliases")));
        beanDefinitionBuilder.addPropertyValue("configurationProperties", resolveProperties(attributes.get("properties")));


        // SqlSessionFactoryBean 的 BeanDefinition
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

        String beanName = (String) attributes.get("value");
        registry.registerBeanDefinition(beanName, beanDefinition);
    }

    private Object resolvePlaceholder(Object value) {
        if (value instanceof String) {
            return environment.resolvePlaceholders((String) value);
        }
        return value;
    }

    private Object[] resolveObjects(Object value) {
        if (value instanceof String[]) {
            return (String[]) value;
        }
        return new Object[] {value};
    }

    private Interceptor[] resolveInterceptor(Object value) {
        List<Interceptor> classes = new ArrayList<>();
        if (value instanceof Class[]) {
            try {
                for (Class o : (Class[]) value) {
                    classes.add((Interceptor) o.newInstance());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Interceptor[] result = new Interceptor[classes.size()];
        return classes.toArray(result);
    }
    private TypeHandler<?>[] resolveTypeHandlers(Object value) {
        List<TypeHandler<?>> typeHandlers = new ArrayList<>();
        if (value instanceof Class[]) {
            try {
                for (Class o : (Class<?>[]) value) {
                    typeHandlers.add((TypeHandler<?>) o.newInstance());
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        TypeHandler<?>[] result = new TypeHandler<?>[typeHandlers.size()];
        return typeHandlers.toArray(result);
    }

    private Properties resolveProperties(Object value) {
        Properties properties = new Properties();
        if (value instanceof String) {
            Resource resource = this.resourceLoader.getResource((String) value);

            try (InputStream is = resource.getInputStream()) {
                properties.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
