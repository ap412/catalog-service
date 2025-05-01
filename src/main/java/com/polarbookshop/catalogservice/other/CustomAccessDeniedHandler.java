package com.polarbookshop.catalogservice.other;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    //Access Denied / unauthorized has handle method when failures occur
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exc) throws IOException {
        Logger logger = LoggerFactory.getLogger("Logback");
        logger.info("CustomAccessDeniedHandler here");

        String errorMessage = "{\"message\": \"Unauthorized for downstream service. No rights.\"}";
        // Set the status code 403 and response body
        response.addHeader("Content-Type", "application/json");
        response.sendError(HttpStatus.FORBIDDEN.value(), errorMessage);
    }

}
