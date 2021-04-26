package com.vavv.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("\n####### CorsFilter initializing\n\n");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("\n\n\n CorsFilter doFilter method..!!!\n\n");

        HttpServletRequest hsReq = (HttpServletRequest)servletRequest;
        HttpServletResponse hsRes = (HttpServletResponse) servletResponse;

        // TODO: get allowed origins from the properties file.
        hsRes.setHeader("Access-Control-Allow-Origin", hsReq.getHeader("Origin"));

        filterChain.doFilter(hsReq, hsRes);

    }

    @Override
    public void destroy() {

    }

}

