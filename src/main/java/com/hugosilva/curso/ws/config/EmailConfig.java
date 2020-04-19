package com.hugosilva.curso.ws.config;

import com.hugosilva.curso.ws.services.email.EmailService;
import com.hugosilva.curso.ws.services.email.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class EmailConfig {

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }
}
