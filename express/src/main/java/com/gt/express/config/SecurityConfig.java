package com.gt.express.config;


import com.gt.express.filters.CustomAuthenticationFilter;
import com.gt.express.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();

        http.authorizeRequests().antMatchers(GET, "/api/users/**").hasAnyAuthority("ROLE_EMPLOYEE");
        http.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST,"/store/addstore/**").hasAnyAuthority("ROLE_EMPLOYEE");

        http.authorizeRequests().antMatchers(GET,"/stores/**").hasAnyAuthority("ROLE_EMPLOYEE");
        http.authorizeRequests().antMatchers(POST,"/item/additem/**").hasAnyAuthority("ROLE_EMPLOYEE");
        http.authorizeRequests().antMatchers(GET,"/items/**").hasAnyAuthority("ROLE_EMPLOYEE");
        http.authorizeRequests().antMatchers(POST,"/drone/adddrone/**").hasAnyAuthority("ROLE_EMPLOYEE");
        http.authorizeRequests().antMatchers(GET,"/drones/**").hasAnyAuthority("ROLE_EMPLOYEE");
        http.authorizeRequests().antMatchers(POST,"/fly_drone/assignpilot/**").hasAnyAuthority("ROLE_EMPLOYEE");
        http.authorizeRequests().antMatchers(GET,"/orders/**").hasAnyAuthority("ROLE_EMPLOYEE");
        http.authorizeRequests().antMatchers(GET,"/customers/**").hasAnyAuthority("ROLE_EMPLOYEE", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT,"order/cancelOrder/**").hasAnyAuthority("ROLE_EMPLOYEE");
        http.authorizeRequests().antMatchers(POST, "/pilot/addpilot/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/customers/addcustomer/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/order/addorder/**").hasAnyAuthority("ROLE_CUSTOMER");
        http.authorizeRequests().antMatchers(PUT, "/order/request/**").hasAnyAuthority("ROLE_CUSTOMER");
        http.authorizeRequests().antMatchers(PUT, "/order/purchase/**").hasAnyAuthority("ROLE_ADMIN");



        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers(
                "/error",
                "/actuator/**",
                "/favicon.ico");
    }
}
