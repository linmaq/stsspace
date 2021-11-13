package com.linmaq.springboot.rpc.provide;

import com.rpc.netty.NettyServer;

/**
 * 启动 一个服务提供者 netty server
 *
 * @Class : ServerBootStrap
 * @author: marin
 * @date : 20:50 2021/11/13
 */
public class ServerBootStrap {
    public static void main(String[] args) {
        NettyServer.startServer("localhost", 7000);
    }
}
