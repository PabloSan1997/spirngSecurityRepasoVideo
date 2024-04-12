package com.practicasecurity.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // configuration one
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.authorizeHttpRequests(auth -> auth
        .requestMatchers("/v1/index2").permitAll()
        .anyRequest().authenticated()
        ).formLogin(l -> l.successHandler(successHandler()).permitAll())
        .sessionManagement(p -> p.sessionCreationPolicy(SessionCreationPolicy.ALWAYS).sessionFixation().newSession()
        .invalidSessionUrl("/v1/session")
        .maximumSessions(1).expiredUrl("/login").sessionRegistry(sessionRegistry()))
        .build();
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    public AuthenticationSuccessHandler successHandler(){
        return ((req, res, authentication)->{
            res.sendRedirect("/v1/index");
        });
    }

}
