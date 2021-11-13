package com.localbrand.config;

import com.localbrand.common.Interface_API;
import com.localbrand.filter.AuthenticationFilter;
import com.localbrand.filter.AuthorizationFilter;
import com.localbrand.model_mapping.Impl.UserMapping;
import com.localbrand.repository.JwtRepository;
import com.localbrand.repository.RoleRepository;
import com.localbrand.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private final JwtRepository jwtRepository;

    private final UserMapping userMapping;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean(), this.jwtRepository, this.userRepository, this.userMapping);
        authenticationFilter.setFilterProcessesUrl(Interface_API.MAIN+Interface_API.API.Auth.LOGIN);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Auth.LOGIN).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Auth.SIGN_UP).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Auth.LOG_OUT).permitAll();
        http.authorizeRequests().antMatchers("/api/webtpf/admin/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new AuthorizationFilter(jwtRepository), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
