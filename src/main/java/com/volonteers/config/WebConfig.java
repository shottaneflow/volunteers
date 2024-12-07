package com.volonteers.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Применяется ко всем путям
                .allowedOrigins("http://localhost:3000")  // Разрешаем запросы с фронтенда
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Разрешаем все необходимые методы
                .allowedHeaders("*")  // Разрешаем все заголовки
                .allowCredentials(true)// Разрешаем отправку cookie
                .maxAge(3600);// Устанавливаем время кэширования preflight запроса (в секундах)
    }
}

