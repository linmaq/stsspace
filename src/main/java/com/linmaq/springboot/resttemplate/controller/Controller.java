package com.linmaq.springboot.resttemplate.controller;

import com.linmaq.springboot.resttemplate.entity.RequestBodyInfos;
import com.linmaq.springboot.resttemplate.service.IInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class : Controller
 * @author: marin
 * @date : 10:36 PM 8/4/2021
 */
@RestController
@Slf4j
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

    @PostMapping("/testJSR303")
    public ResponseEntity testJSR303(@Valid @RequestBody RequestBodyInfos requestBodyInfos, Errors errors) throws URISyntaxException {
        System.out.println("---------------------");
        System.out.println(errors.getErrorCount());
        //遍历error信息
        if (errors.getErrorCount() > 0) {
            List<FieldError> fieldErrors = errors.getFieldErrors();
            Map<String, Object> map = new HashMap<>(fieldErrors.size());
            for (FieldError error : fieldErrors) {
                System.out.println(error.getField() + "---" + error.getDefaultMessage());
                map.put("field", error.getField());
                map.put("errorMsg", error.getDefaultMessage());

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
        log.info(requestBodyInfos.toString());
        return ResponseEntity.status(HttpStatus.OK).body("success...");
    }
}
