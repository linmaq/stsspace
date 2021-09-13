package com.linmaq.springboot.validation.controller;

import com.linmaq.springboot.validation.annotation.ValidationPayload;
import com.linmaq.springboot.validation.bean.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Class : TestController
 * @author: marin
 * @date : 21:30 2021/9/13
 */
@RestController
public class TestController {

    @PostMapping("test01")
    public ResponseEntity test01(@ValidationPayload UserInfo info) {
        return ResponseEntity.status(HttpStatus.OK).body("hello");
    }
}
