package com.promtechsoft.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Instant start = Instant.now();

        // Оборачиваем запрос и ответ для чтения тела
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = Duration.between(start, Instant.now()).toMillis();

            String method = request.getMethod();
            String uri = request.getRequestURI();
            String queryString = request.getQueryString();
            int status = response.getStatus();
            String clientIp = request.getRemoteAddr();

            log.info("HTTP {} {} {} | Status: {} | Duration: {}ms | IP: {}",
                    method,
                    uri + (queryString != null ? "?" + queryString : ""),
                    "from",
                    status,
                    duration,
                    clientIp
            );

            responseWrapper.copyBodyToResponse();
        }
    }
}