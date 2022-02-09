package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.domain.security.JWTutil;
import com.devsimple.springmvc.domain.security.UserSS;
import com.devsimple.springmvc.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTutil jwTutil;

    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToke(HttpServletResponse response){
        UserSS userSS = UserService.authenticated();
        String token = jwTutil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer" + token);
        return ResponseEntity.noContent().build();
    }
}
