package com.lin.alibaba.nio;

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
public class NioServer {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9090));
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        Handler handler = new Handler(1024);

        while (true){
            if (selector.select(3000) == 0){
                System.out.println("等待请求超时。。。。。。。");
                continue;
            }

            System.out.println("处理请求。。。。。。");

            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

            while (keyIter.hasNext()){
                SelectionKey key = keyIter.next();

                try {
                    if (key.isAcceptable()){
                        handler.handlerAccept(key);
                    }
                    if (key.isReadable()){
                        handler.handlerRead(key);
                    }
                }catch (Exception e){
                    keyIter.remove();
                    continue;
                }

                keyIter.remove();
            }
        }
    }

    private static class Handler{
        private int bufferSize = 1024;
        private String localCharset = "utf-8";

        public Handler() {
        }

        public Handler(int bufferSize, String localCharset) {
            if (bufferSize > 0){
                this.bufferSize = bufferSize;
            }
            if (localCharset != null){
                this.localCharset = localCharset;
            }
        }

        public Handler(int bufferSize) {
            this(bufferSize,null);
        }

        public Handler(String localCharset) {
            this(-1,localCharset);
        }

        public void handlerAccept(SelectionKey key) throws Exception{
            SocketChannel accept = ((ServerSocketChannel) key.channel()).accept();
            accept.configureBlocking(false);
            accept.register(key.selector(),SelectionKey.OP_READ,ByteBuffer.allocate(bufferSize));

        }
        public void handlerRead(SelectionKey key) throws IOException {
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();

            if (sc.read(buffer) == -1){
                sc.close();
            }else {
                buffer.flip();
                String receivedString = Charset.forName(localCharset).newDecoder().decode(buffer).toString();
                System.out.println("received from client:"+receivedString);

                String sendString = "received data:"+receivedString;
                buffer = ByteBuffer.wrap(sendString.getBytes());
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
