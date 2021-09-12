package com.linmaq.springboot.niochat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Class : GroupChatClient
 * 1. 连接服务器
 * 2. 发送消息
 * 3，接受消息
 * @author: marin
 * @date : 22:14 2021/9/6
 */
public class GroupChatClient {

    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    /**
     * 完成初始化工作
     */
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        //    连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        //    设置非阻塞
        socketChannel.configureBlocking(false);
        //    将channel注册到channel
        socketChannel.register(selector, SelectionKey.OP_READ);
        //    得到username
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is connected...");
    }

    /**
     * 向服务器发送消息
     *
     * @param info 消息
     */
    public void sendMsg(String info) {
        info = username + "say : " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 读取回复的消息
     */

    public void readInfo() {

        try {
            int readChannel = selector.select();
            // 有事件发生通道
            if (readChannel > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        //    得到相关的通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //    得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        sc.read(buffer);
                        //    把读到的缓冲区的数据转字符串
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());

                    }
                }
                iterator.remove();
            } else {
                //  System.out.println("没有可用的通道...");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();

        //    启动线程
        //启动一个线程,每个 3 秒，读取从服务器发送数据
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    chatClient.readInfo();
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        //    发送数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            chatClient.sendMsg(line);
        }
    }
}
