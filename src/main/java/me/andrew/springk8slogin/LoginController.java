package me.andrew.springk8slogin;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/my")
    public String my(){
        return "my";
    }

}
