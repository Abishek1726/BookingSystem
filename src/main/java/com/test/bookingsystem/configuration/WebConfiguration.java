package com.test.bookingsystem.configuration;

import com.test.bookingsystem.filter.UserAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
    @Autowired
    UserAuthorizationFilter userAuthorizationFilter;

    @Bean
    public FilterRegistrationBean<UserAuthorizationFilter> filterRegistrationBean(){
        FilterRegistrationBean<UserAuthorizationFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(userAuthorizationFilter);
        registrationBean.addUrlPatterns("/v1/*");

        return registrationBean;
    }
}
