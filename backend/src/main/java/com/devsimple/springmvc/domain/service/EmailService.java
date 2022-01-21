package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.model.Pedido;
import org.springframework.mail.SimpleMailMessage;



public interface EmailService {

    void enviarEmailConfirmacao(Pedido pedido);

    void enviarEmail(SimpleMailMessage msg);
}
