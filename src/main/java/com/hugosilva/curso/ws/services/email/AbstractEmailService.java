package com.hugosilva.curso.ws.services.email;

import com.hugosilva.curso.ws.domain.User;
import com.hugosilva.curso.ws.domain.VerificationToken;
import com.hugosilva.curso.ws.services.UserService;
import com.hugosilva.curso.ws.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.UUID;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;
    @Value("${default.url}")
    private String contextPath;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserService userService;

    @Override
    public void sendConfirmationHtmlEmail(User user, VerificationToken vToken){
        try {
            MimeMessage mimeMessage = prepareMimeMessageFromUser(user, vToken);
            sendHtmlEmail(mimeMessage);
        } catch (MessagingException msg) {
            throw new ObjectNotFoundException(String.format("Erro ao tentar enviar o e-mail"));
        }
    }

    protected MimeMessage prepareMimeMessageFromUser(User user, VerificationToken vToken) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setFrom(this.sender);
        mimeMessageHelper.setSubject("Confirmação de Registro");
        mimeMessageHelper.setSentDate(new Date((System.currentTimeMillis())));
        mimeMessageHelper.setText(htmlFromTemplateUser(user,vToken), true);
        return mimeMessage;
    }

    protected String htmlFromTemplateUser(User user, VerificationToken vToken){
        String token = UUID.randomUUID().toString();
        if (vToken == null) {
            this.userService.createVerificationTokenForUser(user, token);
        } else {
            token = vToken.getToken();
        }
        String confirmationUrl = this.contextPath + "/api/public/regitrationConfirm/users?token="+token;
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("confirmationUrl", confirmationUrl);
        return templateEngine.process("email/registerUser", context);
    }

}
