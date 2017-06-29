package com.lin.alibaba.http;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by lwb on 2017/6/26.
 */
public class HttpServer {
    public static void main(String[] args) throws IOException {
        //创建ServerScoketChannel，监听8081端口
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8081));

        ssc.configureBlocking(false);

        Selector selector = Selector.open();

        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            if (selector.select(3000) == 0){
                System.out.println("等待请求超时。。。。。。。");
                continue;
            }

            System.out.println("处理请求。。。。。。");

            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

            while (keyIter.hasNext()){
                SelectionKey key = keyIter.next();

                new Thread(new Handler(key)).start();

                keyIter.remove();
            }
        }

    }

    private static class Handler implements Runnable{
        private int bufferSize = 1024;
        private String localCharset = "utf-8";
        private SelectionKey key;


        public Handler(SelectionKey key) {
            this.key = key;
        }

        public void handlerAccept() throws Exception{
            SocketChannel accept = ((ServerSocketChannel) key.channel()).accept();
            accept.configureBlocking(false);
            accept.register(key.selector(),SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));

        }
        public void handlerRead() throws IOException {
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();

            if (sc.read(buffer) == -1){
                sc.close();
            }else {
                buffer.flip();
                String receivedString = Charset.forName(localCharset).newDecoder().decode(buffer).toString();

                //控制台打印请求报文头
                String[] requestMessage = receivedString.split("\r\n");
                for (String s:requestMessage){
                    System.out.println(s);
                    if (StringUtils.isEmpty(s)){
                        break;
                    }
                }

                //控制台打印首行信息
                String[] firstLine = requestMessage[0].split(" ");
                System.out.println();
                System.out.println("Method:\t"+firstLine[0]);
                System.out.println("url:\t"+firstLine[1]);
                System.out.println("Http Version:\t"+firstLine[2]);
                System.out.println();

                //返回客户端
                StringBuilder sendString = new StringBuilder();
                sendString.append("HTTP/1.1 200 OK\r\n");
                sendString.append("Content-Type:text/html;charset:"+localCharset+"\r\n");
                sendString.append("\r\n");
                sendString.append("<html><head><title>显示报文</title></head><body>");
                sendString.append("接收到的请求报文是：<br/>");
                for (String s:requestMessage){
                    sendString.append(s+"<br/>");
                }
                sendString.append("</body></html>");
                buffer = ByteBuffer.wrap(sendString.toString().getBytes(localCharset));
                sc.write(buffer);
                sc.close();
            }
        }

        public void run() {
            try {
                if (key.isAcceptable()){
                    handlerAccept();
                }
                if (key.isReadable()){
                    handlerRead();
                }
            }catch (Exception e){

            }
        }
    }


}
