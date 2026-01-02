package com.EstudosJpaSpring.livariaapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginViewController {
    @GetMapping("/login")
    public  String loginPage(){
        return "login";
    }
}
