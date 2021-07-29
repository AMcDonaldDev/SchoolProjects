package org.cmsc495.bpo.configs;

import java.util.List;

import org.cmsc495.bpo.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

@SuppressWarnings("all")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    protected static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    private AuthenticationService authenticationService;
    private UserDetailsService userDetailsService;
    private AuthenticatedUserLogoutHandler logoutHandler;

    @Autowired
    public WebSecurityConfig(
        AuthenticationService authenticationService,
        UserDetailsService userDetailsService,
        AuthenticatedUserLogoutHandler logoutHandler
    ) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
        this.logoutHandler = logoutHandler;
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
                .and()
            .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/logout")
                .logoutSuccessHandler(logoutHandler)
                .permitAll()
                .and()
            .authorizeRequests()
                // Allow home page and calendar to be reached without 
                // requiring Users to be authenticated
                .antMatchers("/", "/home", "/calendar/**", "/user/signup", "/park/all").permitAll()
                .and()
            .authorizeRequests()
                // Require Users to authenticate for User-related actions
                .antMatchers("/user/**").authenticated()
                .anyRequest().permitAll()
                .and()
            .httpBasic()   
                .and()
            .csrf().disable();
            log.info("Web Security Configuration Loaded");
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationService).eraseCredentials(false);
    }
}