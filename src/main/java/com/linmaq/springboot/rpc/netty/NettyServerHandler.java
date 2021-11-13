package com.linmaq.springboot.rpc.netty;

import com.rpc.customer.ClientBootStrap;
import com.rpc.provide.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * server handler
 *
 * @Class : NettyServerHandler
 * @author: marin
 * @date : 20:59 2021/11/13
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 获取客户端发送的消息，并进行处理
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg = " + msg);

        // 调用 服务器的api时，需要定义一个协议, 例如：以“helloNetty#hi#”开头
        if (msg.toString().startsWith(ClientBootStrap.PARAM_NAME)) {
            String hello = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(hello);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
