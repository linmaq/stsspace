package com.linmaq.springboot.resttemplate.service.impl;

import com.linmaq.springboot.resttemplate.service.IInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Class : InfoServiceImpl
 * @author: marin
 * @date : 9:47 PM 8/5/2021
 */
@Service
public class InfoServiceImpl implements IInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("payload")
    private String payLoad;
    @Value("endpoint")
    private String endpoint;

    @Override
    public String getInfos() throws URISyntaxException {

        HttpMethod method = HttpMethod.POST;
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(payLoad, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(new URI(endpoint), method, entity, String.class);
        return exchange.getBody();
    }
}
