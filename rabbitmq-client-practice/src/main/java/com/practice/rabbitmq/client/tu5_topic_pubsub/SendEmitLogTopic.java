package com.practice.rabbitmq.client.tu5_topic_pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Luo Bao Ding
 * @since 2019/2/14
 */
public class SendEmitLogTopic {

    public static final String EXCHANGE_NAME = "logs_topic";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("dev.ufotosoft.com");

        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String[] keys = new String[]{"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox"
                    , "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox", "orange", "quick.orange.male.rabbit"
                    , "lazy.orange.male.rabbit"};

            int i = 0;
            for (String key : keys) {
                String message = "i am logs " + (i++);
                channel.basicPublish(EXCHANGE_NAME, key, null, message.getBytes());
                System.out.println(" [x] Sent '" + key + "':'" + message + "'");
            }

        }
    }
}
