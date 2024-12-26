package com.example.backend.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Cấu hình message broker
        registry.enableSimpleBroker("/topic", "/queue"); // Cho phép gửi message đến client
        registry.setApplicationDestinationPrefixes("/app"); // Prefix cho các message từ client gửi lên
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint WebSocket
        registry.addEndpoint("/ws") // Kết nối WebSocket
                .setAllowedOrigins("*") // Cho phép các nguồn gốc khác nhau (hoặc thay * bằng domain của bạn)
                .withSockJS(); // Hỗ trợ fallback nếu WebSocket không khả dụng
    }
}
