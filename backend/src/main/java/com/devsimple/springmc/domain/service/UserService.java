package com.devsimple.springmc.domain.service;

import org.springframework.security.core.context.SecurityContextHolder;
import com.devsimple.springmc.domain.security.UserSS;

public class UserService {

    public static UserSS authenticated() {
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
