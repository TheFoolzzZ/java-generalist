package org.geektimes.projects.springnative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringNativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNativeApplication.class, args);
    }
    @RestController
    class HelloController {
        @GetMapping("/")
        public String hello() {
            return "hello";
        }
    }
}
