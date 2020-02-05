package com.fashionista.api.security;

import com.fashionista.api.exceptions.CustomAccessDeniedHandler;
import com.fashionista.api.exceptions.JwtAuthenticationEntryPoint;
import com.fashionista.api.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.fashionista.api.constants.RouteConstants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private JwtAuthenticationEntryPoint entryPoint;
    private CustomUserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtAuthenticationFilter authenticationFilter;
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfiguration(JwtAuthenticationEntryPoint entryPoint, CustomUserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder, JwtAuthenticationFilter authenticationFilter, CustomAccessDeniedHandler accessDeniedHandler) {
        this.entryPoint = entryPoint;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationFilter = authenticationFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/**/*.jpg",
                        "/**/*.jfif",
                        AUTH_ROOT.concat(AUTH_REGISTER),
                        AUTH_ROOT.concat(AUTH_LOGIN),
                        PRODUCT_ROOT.concat(PRODUCTS_GET),
                        PRODUCT_ROOT.concat(PRODUCT_GET),
                        PRODUCT_ROOT.concat(PRODUCTS_SEARCH),
                        PRODUCT_ROOT.concat(PRODUCT_GET_REVIEWS),
                        TAG_ROOT.concat(TAG_SEARCH),
                        TAG_ROOT.concat(TAG_GET),
                        TAG_ROOT.concat(TAG_GET_ALL)
                )
                .permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
