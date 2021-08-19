package com.yehor.onlineshop.web.filter;

import com.yehor.onlineshop.service.SecurityService;
import com.yehor.onlineshop.web.PageGenerator;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecurityFilter implements Filter {

    private final SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if (req.getRequestURI().equals("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Cookie[] cookies = req.getCookies();

        // this check might seem redundant, but it actually adds a layer of fail-protection
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    if (securityService.isTokenValid(cookie.getValue())) {
                        PageGenerator.allData.put("isUserLoggedIn", true);
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }
            }
        }
        PageGenerator.allData.put("isUserLoggedIn", false);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
