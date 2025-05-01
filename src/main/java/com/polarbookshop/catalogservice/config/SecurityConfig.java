package com.polarbookshop.catalogservice.config;

import com.polarbookshop.catalogservice.other.CustomAccessDeniedHandler;
import com.polarbookshop.catalogservice.other.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/", "/books/**").permitAll()
                        .anyRequest().hasRole("customer") //not apply for permitAll settings
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                //Each request must include an Access Token, so there’s no need to keep
                //a user session alive between requests. We want it to be stateless.
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Since the authentication strategy is stateless and doesn’t involve a browser-based client, we
                //can safely disable the CSRF protection.
                .csrf(AbstractHttpConfigurer::disable)
                /*
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                 */
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        //Defines a converter to map claims to GrantedAuthority objects
        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        //Applies the “ROLE_” prefix to each user role
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        //Extracts the list of roles from the roles claim
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        //Defines a strategy to convert a JWT. We’ll only customize how to build granted authorities out of it.
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

}
