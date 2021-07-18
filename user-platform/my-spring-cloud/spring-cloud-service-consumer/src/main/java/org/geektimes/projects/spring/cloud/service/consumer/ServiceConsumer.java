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
package org.geektimes.projects.spring.cloud.service.consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

/**
 * 服务消费方应用引导类
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = "org.geektimes.projects.spring.cloud")
@EnableDiscoveryClient
@RestController
@EnableFeignClients(basePackages = "org.geektimes.projects.spring.cloud.service")
public class ServiceConsumer {

//    @StreamListener("input")  // 耦合 Spring Cloud Stream API
//    public void onStreamMessage(Message<?> message) {
//        System.out.println("@StreamListener 消息内容：" + message.getPayload());
//    }

    // Spring Cloud Stream 3.0+ 函数接口方式
    @Bean
    public Consumer<String> message() {
        return message -> {
            System.out.println("Consumer 消息内容：" + message);
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceConsumer.class)
                .run(args);
    }
}
