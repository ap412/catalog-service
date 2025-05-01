package com.polarbookshop.catalogservice.other;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        Logger logger = LoggerFactory.getLogger("Logback");
        logger.info("AuthenticationEntryPoint here");

        response.addHeader("access_denied_reason", "authentication_required");
        response.sendError(403, "Access Denied from AuthenticationEntryPoint");
    }
}
