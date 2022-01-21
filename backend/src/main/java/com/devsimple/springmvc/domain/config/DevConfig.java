package com.devsimple.springmvc.domain.config;

import com.devsimple.springmvc.domain.service.DatabaseService;
import com.devsimple.springmvc.domain.service.EmailService;
import com.devsimple.springmvc.domain.service.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DatabaseService databaseService;

    @Value("${spring.jpa.hibernate.dll-auto")
    private String strategy;

    @Bean
    public boolean instanciaDatabase() throws ParseException{
        if (!"create".equals(strategy)) {
            return false;
        }

        databaseService.instanciaTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }

}
