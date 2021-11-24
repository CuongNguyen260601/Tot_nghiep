package com.localbrand.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
public class TemplateServiceImpl {

    private final SpringTemplateEngine springTemplateEngine;

    public TemplateServiceImpl(SpringTemplateEngine springTemplateEngine) {
        this.springTemplateEngine = springTemplateEngine;
    }

    public String buildContent(String template, Map<String, Object> variables, Locale locale) throws IOException {
            Context context = new Context(locale, variables);
            return springTemplateEngine.process(template, context);
    }

}
