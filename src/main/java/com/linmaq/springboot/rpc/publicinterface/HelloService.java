package com.linmaq.springboot.rpc.publicinterface;

/**
 * 公用接口，服务提供方和消费方都需要的
 *
 * @Class : HelloService
 * @author: marin
 * @date : 20:43 2021/11/13
 */
public interface HelloService {
    String hello(String msg);
}
