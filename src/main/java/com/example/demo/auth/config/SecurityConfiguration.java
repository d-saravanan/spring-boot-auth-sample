package com.example.demo.auth.config;

import com.example.demo.auth.filters.TrustedClientsAuthenticationFilter;
import com.example.demo.auth.filters.UserNamePasswordAuthFilter;
import com.example.demo.auth.filters.UsernamePasswordAuthorizationFilter;
import com.example.demo.auth.providers.TrustedClientsAuthProvider;
import com.example.demo.auth.providers.UserNamePasswordAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/*
 * @author: dsaravanan
 */

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    UserNamePasswordAuthProvider userNamePasswordAuthProvider;

    @Autowired
    TrustedClientsAuthProvider trustedClientsAuthProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                //.antMatchers(POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new UserNamePasswordAuthFilter(authenticationManager()))
                .addFilter(new UsernamePasswordAuthorizationFilter(authenticationManager()))
                .addFilter(new TrustedClientsAuthenticationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(userNamePasswordAuthProvider);
        auth.authenticationProvider(trustedClientsAuthProvider);
    }
}
