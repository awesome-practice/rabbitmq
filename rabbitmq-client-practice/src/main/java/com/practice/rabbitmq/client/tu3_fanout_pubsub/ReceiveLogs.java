package com.practice.rabbitmq.client.tu3_fanout_pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Luo Bao Ding
 * @since 2019/2/13
 */
public class ReceiveLogs {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("dev.ufotosoft.com");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicConsume(queueName, true, (consumerTag, message) -> {

            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + msg + "'");

        }, consumerTag -> {
        });


    }
}
