package com.localbrand.service.impl;

import com.localbrand.dto.MailMessageDTO;
import com.localbrand.entity.User;
import com.localbrand.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;
    private final TemplateServiceImpl templateService;
    public static String linkShop = "http://localhost:8080/";
    private final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    List<MailMessageDTO> list = new ArrayList<>();

    public MailServiceImpl(JavaMailSender emailSender, TemplateServiceImpl templateService) {
        this.emailSender = emailSender;
        this.templateService = templateService;
    }

    @Override
    public String sendEmail(MailMessageDTO mailMessageDTO) throws MessagingException,IOException {

        this.log.info("send email for : " + mailMessageDTO.getTo());

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        String html = templateService.buildContent(mailMessageDTO.getContent(),mailMessageDTO.getVariables(), mailMessageDTO.getLocale());

        helper.setTo(mailMessageDTO.getTo());
        helper.setSubject(mailMessageDTO.getSubject());
        helper.setText(html,true);

        String[] cc = mailMessageDTO.getCc();
        if(cc != null && cc.length > 0) {
            helper.setCc(cc);
        }

        String[] bcc = mailMessageDTO.getBcc();
        if(bcc != null && bcc.length > 0) {
            helper.setBcc(bcc);
        }

        String[] attachments = mailMessageDTO.getAttachments();
        if(attachments != null && attachments.length > 0) {
            for(String path: attachments) {
                File file = new File(path);
                helper.addAttachment(file.getName(), file);
            }
        }

        this.log.info("send email for : " + mailMessageDTO.getTo() + " okee");

        emailSender.send(message);
        return "okee";
    }

    @Override
    public String sendEmailForgotPassword(User user) {

        this.log.info("send email forgot password : " + user.getEmail());

        MailMessageDTO mailMessageDTO = new MailMessageDTO();

        Map<String, Object> variables = new HashMap<>();
        variables.put("nameUser",user.getLastName());
        variables.put("newPassword",user.getPasswordUser());
        variables.put("linkShop",linkShop);

        mailMessageDTO.setTo(user.getEmail());
        mailMessageDTO.setSubject("Reset Password");
        mailMessageDTO.setContent("ResetPassword");
        mailMessageDTO.setVariables(variables);
        list.add(mailMessageDTO);
        return "oke";
    }

    @Scheduled(fixedRate  = 500)
    public void run() {
        while(!list.isEmpty()) {
            MailMessageDTO mail = list.remove(0);
            try {
                this.sendEmail(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
