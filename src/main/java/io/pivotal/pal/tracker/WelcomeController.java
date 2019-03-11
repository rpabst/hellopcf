package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Value("${welcome.message}")
    public String msg;


    public WelcomeController() {
    }

    public WelcomeController(String msg) {
        this.msg = msg;
    }

    @GetMapping("/")
    public String sayHello() {

        return msg;
    }
}