package ru.com.document.application.security;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.com.document.application.security.jwt.JwtFilter;
import ru.com.document.application.security.jwt.JwtUtil;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER = "USER";
    private static final String ADMIN = "WPS-ADMIN";

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/actuator/**"
    };

    private final JwtUtil jwtUtil;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/wps/admin/**").hasAuthority(ADMIN)
                .antMatchers("/api/v1/wps/dict/**").hasAnyAuthority(USER, ADMIN)
                .antMatchers("/api/document/**").hasAnyAuthority(USER, ADMIN)
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .headers().cacheControl();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilterBean() throws Exception {
        return new JwtFilter();
    }

    @Bean
    public FilterRegistrationBean preAuthTenantContextInitializerFilterRegistration(JwtFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        //prevent spring boot to add this filter to filter chain automatically
        registration.setEnabled(false);
        return registration;
    }
}
