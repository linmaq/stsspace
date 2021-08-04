package com.linmaq.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Class : Controller
 * @author: marin
 * @date : 10:36 PM 8/4/2021
 */
@RestController
public class Controller {

    @GetMapping({"/", "/index"})
    public String index() {
        return "{msg : hello Spring Boot index!!!}";
    }
}
