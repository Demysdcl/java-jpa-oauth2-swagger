package com.hugosilva.curso.ws.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfigCorsRegistery  implements WebMvcConfigurer{


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/*")
                .allowedOrigins("*")
                .allowedMethods("POST, PUT, GET, OPTIONS, DELETE")
                .allowedHeaders("*")
                .allowCredentials(false).maxAge(3600);
    }
}