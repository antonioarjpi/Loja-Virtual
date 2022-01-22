package com.devsimple.springmvc.domain.service;

import com.devsimple.springmvc.domain.model.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;


public interface EmailService {

    void enviarEmailConfirmacao(Pedido pedido);

    void enviarEmail(SimpleMailMessage msg);

    void enviarEmailConfirmacaoHtml(Pedido pedido);

    void enviarEmailHtml(MimeMessage msg);
}
