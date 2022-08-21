package com.devsimple.springmc.domain.config;

import com.devsimple.springmc.domain.service.DatabaseService;
import com.devsimple.springmc.domain.service.EmailService;
import com.devsimple.springmc.domain.service.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DatabaseService databaseService;

    @Bean
    public boolean instanciaTestDatabase() throws ParseException {
        databaseService.instanciaTestDatabase();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}
