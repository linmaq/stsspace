package com.linmaq.springboot.rpc.provide;


import com.linmaq.springboot.rpc.publicinterface.HelloService;
import org.apache.commons.lang3.StringUtils;

/**
 * @Class : HelloServiceImpl
 * @author: marin
 * @date : 20:45 2021/11/13
 */
public class HelloServiceImpl implements HelloService {
    /**
     * 当消费方调用时候，return response
     *
     * @param msg 请求信息
     * @return 返回结果
     */
    @Override
    public String hello(String msg) {
        System.out.println("accept client message = " + msg);
        if (StringUtils.isNoneEmpty(msg)) {
            return "Hello client, i have accept your message[" + msg + "]";
        }
        return "Hello client, i have accept your message";
    }
}
