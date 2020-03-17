package com.lukmie.timeports.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.servlet.DispatcherType.ASYNC;
import static javax.servlet.DispatcherType.ERROR;
import static javax.servlet.DispatcherType.FORWARD;
import static javax.servlet.DispatcherType.INCLUDE;
import static javax.servlet.DispatcherType.REQUEST;

@Slf4j
@Order
@Component
@WebFilter(urlPatterns = {"/", "/*"}, asyncSupported = true, dispatcherTypes = {REQUEST, ASYNC, ERROR, FORWARD, INCLUDE})
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(request, response);
        if (log.isInfoEnabled()) {
            logTime(request, start);
        }
    }

    private void logTime(HttpServletRequest request, long start) {
        long nanos = System.currentTimeMillis() - start;
        String headers = getHeadersAsString(request);
        String username = "";
        if (Objects.nonNull(request.getUserPrincipal())) {
            username = request.getUserPrincipal().getName();
        }
        if (log.isDebugEnabled()) {
            log.debug("request: user: {}, url: {}, time: {} ms, params: {}, headers: {}", username, request.getRequestURL(),
                    nanos, createMessage(request, "", ""), headers);
        } else {
            log.info("request: user: {}, url: {}, time: {} ms, params: {}", username, request.getRequestURL(),
                    nanos, createMessage(request, "", ""));
        }
    }

    private String getHeadersAsString(HttpServletRequest request) {
        Function<String, Pair<String, Enumeration<String>>> mapHeaderNameToHeaders = s -> Pair
                .of(s, request.getHeaders(s));
        return Collections.list(request.getHeaderNames()).stream()
                .map(mapHeaderNameToHeaders)
                .flatMap(LoggingFilter::mapPairToStrings)
                .collect(Collectors.joining("\n"));
    }

    private String createMessage(HttpServletRequest request, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(prefix).append("uri=").append(request.getRequestURI());

        String queryString = request.getQueryString();
        if (queryString != null) {
            msg.append('?').append(queryString);
        }

        msg.append(";client=").append(request.getRemoteAddr()).append(suffix);
        return msg.toString();
    }

    private static Stream<? extends String> mapPairToStrings(Pair<String, Enumeration<String>> header) {
        return Collections.list(header.getSecond()).stream().map(s -> header.getFirst() + "= " + s);
    }
}


