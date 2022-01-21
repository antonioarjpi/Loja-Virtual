package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService{

    @Value("${default.sender")
    private String enviar;

    @Override
    public void enviarEmailConfirmacao(Pedido pedido){
        SimpleMailMessage sm = prepararEmailParaPedido(pedido);
        enviarEmail(sm);
    }

    protected SimpleMailMessage prepararEmailParaPedido(Pedido pedido) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(pedido.getCliente().getEmail());
        sm.setFrom(enviar);
        sm.setSubject("Pedido "+ pedido.getId() + " confirmado!");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(pedido.toString());
        return sm;
    }
}
