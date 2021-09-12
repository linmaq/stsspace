package com.linmaq.springboot.niochat;

import lombok.val;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * @Class : GroupChatServer
 * 1.服务器启动并监听端口 6667
 * 2. 服务器接受客户端信息，并实现转发【上线，离线】
 * @author: marin
 * @date : 21:13 2021/9/6
 */
public class GroupChatServer {
    /**
     * 定义属性
     */
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    /**
     * 构造器
     * 初始化工作
     */
    public GroupChatServer() {
        try {
            //得到选择器
            selector = Selector.open();
            //    listenChannel
            listenChannel = ServerSocketChannel.open();
            //    bind port
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //    setting no blocking
            listenChannel.configureBlocking(false);
            //     将listenChannel 注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //监听
    public void listen() {
        try {

            //    循环处理
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    //    遍历得到的selectionKey
                    val iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出selectedKey
                        val key = iterator.next();

                        //  监听到accept
                        if (key.isAcceptable()) {
                            SocketChannel accept = listenChannel.accept();
                            accept.configureBlocking(false);
                            //注册到 selector
                            accept.register(selector, SelectionKey.OP_READ);
                            System.out.println(accept.getRemoteAddress() + "--> online...");
                        }
                        //通道发生read事件，即可读状态
                        if (key.isReadable()) {
                            //    处理读
                            readData(key);
                        }
                        //    当前的key删除，防止重复操作
                        iterator.remove();
                    }
                } else {
                    System.out.println("waiting....");
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {

        }
    }

    /**
     * 读取客户端消息
     *
     * @param key
     */
    private void readData(SelectionKey key) {

        //    定义一个socket channel
        SocketChannel channel = null;
        try {
            //    取到关联的channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //    根据count的值处理
            System.out.println("count: " + count);
            if (count > 0) {
                //    把缓冲区的数据转字符串
                String str = new String(buffer.array());
                System.out.println("from client : " + str);
                //    向其他客户端转发消息(排除自己)
                sendMsgToOtherClients(str, channel);
            }
        } catch (IOException e) {
            try {
                //offline
                System.out.println(channel.getRemoteAddress() + "--> offline");
                //     取消注册
                key.cancel();
                //    关闭通道
                channel.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void sendMsgToOtherClients(String message, SocketChannel self) throws IOException {
        System.out.println("服务器开始转发消息...");
        //遍历 所有注册到selector的channel，并排除自己
        for (SelectionKey key : selector.keys()) {
            //     key->取出对应的socket channel
            Channel AllChannel = key.channel();
            //    排除自己
            if (AllChannel instanceof SocketChannel && AllChannel != self) {
                //转型
                SocketChannel dest = (SocketChannel) AllChannel;
                //    将msg 储存到buffer
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
                //    将buffer的数据写入channel
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        new GroupChatServer().listen();
    }
}
