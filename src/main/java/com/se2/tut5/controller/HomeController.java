package com.se2.tut5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "home";  // Make sure this returns "home" instead of "redirect:/employee/"
    }
}
