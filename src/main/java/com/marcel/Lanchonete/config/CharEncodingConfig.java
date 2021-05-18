package com.marcel.Lanchonete.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class CharEncodingConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CharEncodingConfig.class);

    @Bean
    CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        LOGGER.info("Application character encoding: "+filter.getEncoding());
        return filter;
    }
    

}