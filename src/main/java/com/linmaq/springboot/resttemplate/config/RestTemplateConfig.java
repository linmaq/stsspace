package com.linmaq.springboot.resttemplate.config;

import com.linmaq.springboot.resttemplate.exception.handler.RestTemplateErrorHandling;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Class : RestTemplateConfig
 * @author: marin
 * @date : 10:36 PM 8/4/2021
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 1. init restTemplate bean
     * 2. add restTemplate error handler
     *
     * @param requestFactory
     * @return init bean
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory requestFactory) {
        RestTemplate template = new RestTemplate(requestFactory);
        template.setErrorHandler(new RestTemplateErrorHandling());
        return template;
    }

    /**
     * create requestFactory
     *
     * @return requestFactory
     */
    @Bean
    public ClientHttpRequestFactory requestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpclient());
    }

    @Bean
    public HttpClient httpclient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();

        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(registry);
        manager.setMaxTotal(100);
        manager.setDefaultMaxPerRoute(100);

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(20000)
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(20000)
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setConnectionManager(manager)
                .build();
    }
}
