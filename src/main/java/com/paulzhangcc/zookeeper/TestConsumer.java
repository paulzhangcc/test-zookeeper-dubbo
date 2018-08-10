package com.paulzhangcc.zookeeper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author paul
 * @description
 * @date 2018/7/10
 */
public class TestConsumer {
//    public static final String QUEUE_NAME = "zjf-test";
//    public static void main(String[] args) throws Exception {
//        ConnectionFactory factory = new ConnectionFactory();
//        {
//            factory.setHost("47.94.241.207");
//            factory.setPort(5672);
//            factory.setUsername("guest");
//            factory.setPassword("guest");
//            //factory.setVirtualHost("meme2cmq");
//        }
//        Connection connection = factory.newConnection();
//
//        Channel channel = connection.createChannel();
//        channel.basicQos(2);
//        AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope,
//                                       AMQP.BasicProperties properties, byte[] body)
//                    throws IOException {
//                String message = new String(body, "UTF-8");
//                String name = Thread.currentThread().getName();
//                System.out.println("Thread.name=" + name + " [x] Received '" + message + "'");
//                getChannel().basicAck(envelope.getDeliveryTag(),true);
//            }
//        };
//        channel.basicConsume(QUEUE_NAME,false, consumer);
//    }
public static void main(String[] args) {
    Class<TestEnum> testEnumClass = TestEnum.class;
    Field[] fields = testEnumClass.getFields();
    for (int i = 0; i <fields.length ; i++) {
        System.out.println(fields[i].getName()+":"+fields[i]);
    }

    Method[] methods = testEnumClass.getMethods();
    for (int i = 0; i <methods.length ; i++) {
        System.out.println(methods[i].getName()+":"+methods[i]);
    }
}

}
enum  TestEnum {
    one,two,three;
}
