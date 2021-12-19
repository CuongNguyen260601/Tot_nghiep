package com.localbrand.service.impl;

import com.localbrand.dto.MailMessageDTO;
import com.localbrand.dto.response.statistical.SummaryStatusBillDTO;
import com.localbrand.entity.User;
import com.localbrand.entity.Voucher;
import com.localbrand.service.MailService;
import com.localbrand.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    @Value("${server.port}")
    static Integer port;
    private final JavaMailSender emailSender;
    private final TemplateServiceImpl templateService;
    public static String linkShop = "http://localhost:" + port ;
    private final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
    private final StatisticalService statisticalService;


    List<MailMessageDTO> list = new ArrayList<>();

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
        return "oke";
    }

    @Override
    public String sendEmailBillByDate(List<SummaryStatusBillDTO> list11) {

        this.log.info("send email report by date");

        List<SummaryStatusBillDTO> lisTSendMail = new ArrayList<>();
        list11.forEach(summaryStatusBillDTO -> {
            if(summaryStatusBillDTO.getTotalBill() > 0){
                lisTSendMail.add(summaryStatusBillDTO);
            }
        });

        Date date1 = new Date();


        MailMessageDTO mailMessageDTO = new MailMessageDTO();

        Map<String, Object> variables = new HashMap<>();
        variables.put("date",date1.toString());
        variables.put("prList",lisTSendMail);
        variables.put("linkShop",linkShop);

        mailMessageDTO.setTo("cuongnt2001.06.26@gmail.com");
        mailMessageDTO.setSubject("Report bill of "+date1.toString());
        mailMessageDTO.setContent("ReportBill");
        mailMessageDTO.setVariables(variables);
        list.add(mailMessageDTO);
        return "oke";
    }

    @Override
    public String sendEmailForgotPassword(User user) {

        this.log.info("send email forgot password : " + user.getEmail());

        MailMessageDTO mailMessageDTO = new MailMessageDTO();

        Map<String, Object> variables = new HashMap<>();
        variables.put("nameUser",user.getFirstName()+" "+user.getLastName());
        variables.put("newPassword",user.getPasswordUser());
        variables.put("linkShop",linkShop);

        mailMessageDTO.setTo(user.getEmail());
        mailMessageDTO.setSubject("Reset Password");
        mailMessageDTO.setContent("ResetPassword");
        mailMessageDTO.setVariables(variables);
        list.add(mailMessageDTO);
        return "oke";
    }

    @Override
    public String sendEmailBillSuccess(User user, Voucher voucher) {
        this.log.info("send email bill success : " + voucher.getIdVoucher());

        MailMessageDTO mailMessageDTO = new MailMessageDTO();

        Map<String, Object> variables = new HashMap<>();
        variables.put("discount",voucher.getDiscount().toString()+"%");
        variables.put("code",voucher.getCodeVoucher());
        variables.put("linkShop",linkShop);
        mailMessageDTO.setTo(user.getEmail());
        mailMessageDTO.setSubject("Bill success");
        mailMessageDTO.setContent("Bill_Success");
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
