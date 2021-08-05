package com.linmaq.springboot.resttemplate.controller;

import com.linmaq.springboot.resttemplate.service.IInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Class : Controller
 * @author: marin
 * @date : 10:36 PM 8/4/2021
 */
@RestController
public class Controller {

    @Autowired
    private IInfoService service;

    @GetMapping("/index")
    public String index() {
        return "{msg : hello Spring Boot index!!!}";
    }

    @GetMapping({"/", "/health"})
    public Map<String, Object> health() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("endpoint", "health check");
        map.put("date", LocalDateTime.now());
        map.put("status", "OK");
        return map;
    }

    @PostMapping("/getInfos")
    public String getInfo() throws URISyntaxException {
        return service.getInfos();
    }
}
