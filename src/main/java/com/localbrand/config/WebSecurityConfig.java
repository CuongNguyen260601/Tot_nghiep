package com.localbrand.config;

import com.localbrand.common.Interface_API;
import com.localbrand.common.Role_Enum;
import com.localbrand.filter.AuthenticationFilter;
import com.localbrand.filter.AuthorizationFilter;
import com.localbrand.model_mapping.Impl.UserMapping;
import com.localbrand.repository.JwtRepository;
import com.localbrand.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;


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
        http.csrf().disable()
                .addFilterBefore(new AuthorizationFilter(jwtRepository), UsernamePasswordAuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .cors().configurationSource(corsConfigurationSource()).and();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Auth.LOGIN).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Auth.SIGN_UP).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Auth.REFRESH_TOKEN).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_GET_ALL_USER).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_SEARCH_USER).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_SHOW_USER).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_SHOW_USER_BY_COLOR).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_SHOW_USER_BY_COLOR_AND_SIZE).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_SHOW_ALL).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_HOT).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_NEW).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Product.PRODUCT_RELATED).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Address.ADDRESS_FIND_ALL_PROVINCE).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Address.ADDRESS_FIND_ALL_DISTRICT).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Address.ADDRESS_FIND_ALL_COMMUNE).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Combo.COMBO_FIND_ALL_USER).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Combo.COMBO_FIND_BY_ID_USER).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Combo.COMBO_SEARCH_USER).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Size.SIZE_FIND_EXISTS).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Color.COLOR_FIND_EXISTS).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Category.CATEGORY_TO_SIZE).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.UPLOAD_IMAGE).permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Auth.GET_NEW_PASSWORD).permitAll();
        http.authorizeRequests().antMatchers("/Image/**").permitAll();
        http.authorizeRequests().antMatchers(Interface_API.MAIN+Interface_API.API.Category.Category_Child.CATEGORY_CHILD_FIND_BY_PARENT_ID).permitAll();
        http.authorizeRequests().antMatchers("/api/webtpf/admin/**").hasAnyAuthority(Role_Enum.ROLE_ADMIN.getRole(),Role_Enum.ROLE_EMPLOYEE.getRole());
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        List<String> listMethod = new ArrayList<>();
        listMethod.add("GET");
        listMethod.add("POST");
        listMethod.add("PUT");
        listMethod.add("DELETE");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(listMethod);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
