package com.linmaq.springboot.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @Class : NettyClientHandler
 * @author: marin
 * @date : 21:12 2021/11/13
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    /**
     * quest param
     */
    private String param;

    /**
     * 与服务器的连接创建后，就会被调用,且只会调用一次
     * step 1
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive......");
        context = ctx;
    }

    /**
     * 收到服务器的数据后被调用
     * step 4
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead 1......");
        result = msg.toString();
        //唤醒等待的线程
        notify();
        System.out.println("channelRead 2......");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 被代理对象调用，发送数据给服务器--> wait--> 然后等待被唤醒 -->return result
     * step 3
     *
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call 1......");
        context.writeAndFlush(param);
        //    wait，等待 channelRead 获取到结果后唤醒
        wait();
        System.out.println("call 2......");
        return result;
    }

    /**
     * step 2
     *
     * @param param
     */
    void setParam(String param) {
        System.out.println("setParam......");
        this.param = param;
    }
}
