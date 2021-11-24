package com.localbrand.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailMessageDTO {

    private String from;

    private String to;

    private String[] cc;

    private String[] bcc;

    private String subject;

    private String content;

    private String[] attachments;

    private Map<String, Object> variables ;

    @Builder.Default
    private Locale locale = LocaleContextHolder.getLocale();

    public MailMessageDTO(String to, String subject, String content) {
        this.from = "LocalBrand TPF";
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

}
