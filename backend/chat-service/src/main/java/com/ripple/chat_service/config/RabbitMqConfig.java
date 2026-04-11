package com.ripple.chat_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String CHAT_EXCHANGE = "chat.exchange";
    public static final String CHAT_MESSAGE_QUEUE = "chat.message.queue";
    public static final String CHAT_STATUS_QUEUE = "chat.status.queue";
    public static final String CHAT_MESSAGE_ROUTING_KEY = "chat.message.*";
    public static final String CHAT_STATUS_ROUTING_KEY = "chat.status.*";

    @Bean
    public TopicExchange chatExchange() {
        return new TopicExchange(CHAT_EXCHANGE);
    }

    @Bean
    public Queue chatMessageQueue() {
        return new Queue(CHAT_MESSAGE_QUEUE, true);
    }

    @Bean
    public Queue chatStatusQueue() {
        return new Queue(CHAT_STATUS_QUEUE, true);
    }

    @Bean
    public Binding chatMessageBinding(Queue chatMessageQueue, TopicExchange chatExchange) {
        return BindingBuilder.bind(chatMessageQueue).to(chatExchange).with(CHAT_MESSAGE_ROUTING_KEY);
    }

    @Bean
    public Binding chatStatusBinding(Queue chatStatusQueue, TopicExchange chatExchange) {
        return BindingBuilder.bind(chatStatusQueue).to(chatExchange).with(CHAT_STATUS_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
