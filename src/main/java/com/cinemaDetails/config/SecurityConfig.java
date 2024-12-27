package com.cinemaDetails.config;

import com.cinemaDetails.security.JWTAuthenticationEntryPoint;
import com.cinemaDetails.security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    //Mentioning to spring security to use the custom userDetailsService
    private UserDetailsService userDetailsService;
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JWTAuthenticationFilter jwtAuthenticationFilter){
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //By creating authenticationmanager bean, spring security uses the custom userDetailsService and passwordEndcoder to validate the user or visitor
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        //it has authenticate method which takes InmemoryUserDetailsService or DB data or etc with the help of support method
        return auth.getAuthenticationManager();
    }

    //Overriding the default securityfilterchain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //csrf disable used to disable the login form
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                 .anyRequest().authenticated()
                ) //httpBasic(Customizer.withDefaults()
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                ).sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        //JWT is a stateless authentication

        //This filter should execute before executing the spring security filters
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
