package com.devsimple.springmc.api.controller;

import com.devsimple.springmc.api.dto.EmailDTO;
import com.devsimple.springmc.domain.security.JWTutil;
import com.devsimple.springmc.domain.security.UserSS;
import com.devsimple.springmc.domain.service.AuthService;
import com.devsimple.springmc.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Api(tags = "Auth Controller")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private JWTutil jwTutil;
    private AuthService authService;

    public AuthController(JWTutil jwTutil, AuthService authService) {
        this.jwTutil = jwTutil;
        this.authService = authService;
    }

    @ApiOperation("Atualiza token ")
    @PostMapping("/refresh_token")
    public ResponseEntity<Void> refreshToke(HttpServletResponse response) {
        UserSS userSS = UserService.authenticated();
        String token = jwTutil.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer" + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Esqueci minha senha")
    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
        authService.sendNewPassword(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
