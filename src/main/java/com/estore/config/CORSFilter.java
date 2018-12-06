package com.estore.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Ashutosh on 26 Jun, 2017.
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Headers","Authorization, Content-Type");

        if(req.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString())){
            res.setStatus(HttpServletResponse.SC_OK);
        }else{
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }
}
