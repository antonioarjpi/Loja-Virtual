package com.devsimple.springmc.domain.service;

import com.devsimple.springmc.domain.model.Cliente;
import com.devsimple.springmc.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender")
    private String enviar;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void enviarEmailConfirmacao(Pedido pedido) {
        SimpleMailMessage sm = prepararEmailParaPedido(pedido);
        enviarEmail(sm);
    }

    protected String htmlTemplatePedido(Pedido pedido) {
        Context context = new Context();
        context.setVariable("pedido", pedido);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    public void enviarEmailConfirmacaoHtml(Pedido pedido) {
        try {
            MimeMessage mimeMessage = prepararMimeMessageParaPedido(pedido);
            enviarEmailHtml(mimeMessage);
        } catch (MessagingException e) {
            enviarEmailConfirmacao(pedido);
        }
    }

    protected MimeMessage prepararMimeMessageParaPedido(Pedido pedido) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(pedido.getCliente().getEmail());
        mimeMessageHelper.setFrom(enviar);
        mimeMessageHelper.setSubject("Pedido " + pedido.getId() + " confirmado!");
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(htmlTemplatePedido(pedido), true);
        return mimeMessage;
    }

    protected SimpleMailMessage prepararEmailParaPedido(Pedido pedido) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(pedido.getCliente().getEmail());
        sm.setFrom(enviar);
        sm.setSubject("Pedido " + pedido.getId() + " confirmado!");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(pedido.toString());
        return sm;
    }

    public void esqueciSenha(Cliente cliente, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
        enviarEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(enviar);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }
}
