package com.devask.controller;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();
        boolean isAuth = session.getAttribute("user") != null;
        boolean isLogin = httpRequest.getRequestURI().contains("login");
        boolean isRegister = httpRequest.getRequestURI().contains("user");

        // Exclude routes such as home, questions, tags, comments, and the root ("/") for home page
        boolean isExcluded = httpRequest.getRequestURI().contains("home")
                            || httpRequest.getRequestURI().contains("questions")
                            || httpRequest.getRequestURI().contains("tags")
                            || httpRequest.getRequestURI().contains("comment")
                            || httpRequest.getRequestURI().contains("admin")
                            ||  httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/") ;

        if ((isAuth && isRegister) || isLogin || isExcluded) {
            filterChain.doFilter(httpRequest, httpResponse);
        } else {
            httpResponse.sendRedirect("login");
        }
    }
}
