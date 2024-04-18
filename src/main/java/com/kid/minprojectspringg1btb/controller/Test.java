package com.kid.minprojectspringg1btb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
public class Test {
    @GetMapping
    public String test(Model model) {
        model.addAttribute("name", "Many");
        return "index";
    }
}
