package org.geektimes.projects.spring.cloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RefreshScope
public class HelloController {

    @Value("${user.name}")
    private String userName;
    @Value("${user.age}")
    private int userAge;

    @GetMapping("/hello")
    public String hello() {
        return "hello " + userName + ", your age is " + userAge ;
    }
}
