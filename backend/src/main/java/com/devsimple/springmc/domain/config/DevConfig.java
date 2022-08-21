package com.devsimple.springmc.domain.config;

import com.devsimple.springmc.domain.service.DatabaseService;
import com.devsimple.springmc.domain.service.EmailService;
import com.devsimple.springmc.domain.service.SmtpEmailService;
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
    public boolean instanciaDatabase() throws ParseException {
        if (!"create".equals(strategy)) {
            return false;
        }

        databaseService.instanciaTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }

}
