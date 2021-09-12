package com.linmaq.springboot.valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class : ValidTestController
 * @author: marin
 * @date : 21:46 2021/9/12
 */
@RestController
public class ValidTestController {

    /**
     * @param payment bean
     * @param result  error result
     * @return
     */
    @PostMapping("/test1")
    public ResponseEntity test1(@Valid @RequestBody Payment payment, Errors result) {
        Map<String, Object> map = new LinkedHashMap<>();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (int i = 0; i < fieldErrors.size(); i++) {
                map.put(fieldErrors.get(i).getField(), fieldErrors.get(i).getDefaultMessage());
                System.out.println(Arrays.asList(fieldErrors));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
        return ResponseEntity.status(HttpStatus.OK).body("test1");
    }
}