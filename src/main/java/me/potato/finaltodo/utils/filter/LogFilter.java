package me.potato.finaltodo.utils.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import me.potato.finaltodo.service.exceptions.NoUserInfoInSessionException;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class LogFilter implements Filter {

    private static final String[] whitelist = {"/user/*", "/css/*", "/", "/security/*", "/img/*", "/favicon.ico"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("loginFilter proceed");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String requestURI = httpRequest.getRequestURI();
        boolean isRedirect = false;

            if(isLoginCheckPath(requestURI)) {
                log.info("requestURI : {}", requestURI);
                HttpSession session = httpRequest.getSession();
                if(session==null || session.getAttribute("user") == null) {
                    isRedirect = true;
                }
            }

            if(isRedirect) {
                request.getRequestDispatcher("/filterError").forward(request,response);
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


