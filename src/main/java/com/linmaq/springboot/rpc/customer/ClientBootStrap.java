package com.linmaq.springboot.rpc.customer;

import com.rpc.netty.NettyClient;
import com.rpc.publicinterface.HelloService;

/**
 * @Class : ClientBootStrap
 * @author: marin
 * @date : 21:42 2021/11/13
 */
public class ClientBootStrap {

    public static final String PARAM_NAME = "helloNetty#hi#";

    public static void main(String[] args) {
        //   create customer
        NettyClient customer = new NettyClient();
        // create proxy
        HelloService service = (HelloService) customer.getBean(HelloService.class, PARAM_NAME);

        //   call proxy object method
        String hello = service.hello("RPC POC!!!");
        System.out.println("return response  = " + hello);
    }
}
