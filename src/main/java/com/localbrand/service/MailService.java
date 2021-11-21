package com.localbrand.service;

import com.localbrand.dto.MailMessageDTO;
import com.localbrand.entity.User;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailService {

    String sendEmailForgotPassword(User user) throws MessagingException, IOException;

    String sendEmail(MailMessageDTO mailMessageDTO) throws MessagingException, IOException;
}
