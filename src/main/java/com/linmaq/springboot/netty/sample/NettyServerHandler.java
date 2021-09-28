package com.linmaq.springboot.netty.sample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @Class : NettyServerHandler
 * 1.自定义一个handler需要继承netty 规定好的ChannelInboundHandlerAdapter（规范）
 * 2.
 * @author: marin
 * @date : 22:31 2021/9/23
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取实际数据
     *
     * @param ctx 上下文对象，含有 管道
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //ByteBuf  netty提供的
        ByteBuf buf = (ByteBuf) msg;
        log.info("client message is : {}", buf.toString(StandardCharsets.UTF_8));
        log.info("client address: {}", ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //一般需要对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client~~~", StandardCharsets.UTF_8));
    }

    /**
     * 处理异常 ，一般需要关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
