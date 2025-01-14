package com.example.mainservice.cfg;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AuthCfg {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
          String token = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getCredentials());
          if (token != null && !token.isEmpty()) {
              requestTemplate.header("Authorization", "Bearer " + token);
          }
        };
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
