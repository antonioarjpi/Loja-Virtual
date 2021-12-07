package com.devsimple.springmvc.domain.config;

import com.devsimple.springmvc.domain.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TesteConfig {

    @Autowired
    private DatabaseService databaseService;

    @Bean
    public boolean instanciaDatabase() throws ParseException {
        databaseService.instanciaDatabase();
        return true;
    }
}
