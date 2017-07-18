package com.lin.alibaba.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MsgReceiver {
    private final static String QUEUE_NAME = "hello";  
  
    public static void main(String[] argv) throws IOException, InterruptedException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        // 设置MabbitMQ所在主机ip或者主机名
        factory.setHost("172.18.12.206");
        factory.setUsername("doc");
        factory.setPassword("123456");
        factory.setVirtualHost("smplatform");
        factory.setPort(5672);
        // 打开连接和创建频道，与发送端一样  
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
  
        // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。  
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);  
        // 创建队列消费者  
        QueueingConsumer consumer = new QueueingConsumer(channel);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");  
        // 指定消费队列  
        channel.basicConsume(QUEUE_NAME, true, consumer);  
        while (true) {  
            // nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();  
            String message = new String(delivery.getBody());  
            System.out.println(" [x] Received '" + message + "'");  
        }  
    }  
}  