package com.lucky.thymeleaf.integration;

import com.lucky.framework.annotation.Bean;
import com.lucky.framework.annotation.Configuration;
import com.lucky.web.conf.WebConfig;

/**
 * @author fk
 * @version 1.0
 * @date 2020/12/21 0021 15:17
 */
@Configuration
public class ThymeleafAutoConfig {

    @Bean
    public void setWebConfig(){
        WebConfig webConfig = WebConfig.defaultWebConfig();
        webConfig.setResponse(new ThymeleafResponse());
    }
}
