package com.linmaq.springboot.netty.sample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @Class : NettyServer
 * @author: marin
 * @date : 22:05 2021/9/23
 */
@Slf4j
public class NettyServer {
    @SneakyThrows
    public static void main(String[] args) {
        //    创建boss Group 和 worker group
        //1.创建两个线程组 bossGroup、workerGroup
        //2.bossGroup 仅处理accept，真正的与客户端业务处理的是workerGroup
        //3.两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            //    创建 服务器端启动参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //    使用链式编程进行设置
            //  group：设置线程组
            //  option：设置线程队列得到的连接个数
            //  childOption：设置保持活动连接状态
            //  childHandler： 对workerGroup 的EventLoop 对应的管道设置处理器
            //  childHandler：创建通道初始化
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //   给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });

            log.info("Server is ready.......");
            //binding port and sync
            ChannelFuture future = bootstrap.bind(6668).sync();
            //    对关闭通道进行监听
            future.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
