package me.potato.finaltodo.utils.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
@Component
@Order(1)
public class LogFilter implements Filter {

    private static final String[] whitelist = {"/user/*", "/css/*", "/", "/security/*", "/img/*", "/favicon.ico", "/js/*"};

    @Override
    public void init(FilterConfig filterConfig){
        log.info("log filter init!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String requestURI = httpRequest.getRequestURI();
        boolean isRedirect = false;

            if(isLoginCheckPath(requestURI)) {
                log.info("loginFilter proceed requestURI : {}", requestURI);
                HttpSession session = httpRequest.getSession();
                if(session==null || session.getAttribute("user") == null) {
                    isRedirect = true;
                }
            }

            if(isRedirect) {
                httpResponse.sendRedirect("/user/error");
            }else {
                chain.doFilter(request,response);
            }

    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }

    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}


