package com.devsimple.springmc.domain.service;

import com.devsimple.springmc.domain.model.Cliente;
import com.devsimple.springmc.domain.model.Pedido;

import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;


public interface EmailService {

    void enviarEmailConfirmacao(Pedido pedido);

    void enviarEmail(SimpleMailMessage msg);

    void enviarEmailConfirmacaoHtml(Pedido pedido);

    void enviarEmailHtml(MimeMessage msg);

    void esqueciSenha(Cliente cliente, String novaSenha);
}
