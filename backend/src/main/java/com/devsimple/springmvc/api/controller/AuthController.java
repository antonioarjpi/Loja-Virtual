package com.devsimple.springmvc.api.controller;

import com.devsimple.springmvc.api.dto.EmailDTO;
import com.devsimple.springmvc.domain.security.JWTutil;
import com.devsimple.springmvc.domain.security.UserSS;
import com.devsimple.springmvc.domain.service.AuthService;
import com.devsimple.springmvc.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private AuthService authService;

    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToke(HttpServletResponse response){
        UserSS userSS = UserService.authenticated();
        String token = jwTutil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer" + token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO){
        authService.sendNewPassword(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }


}
