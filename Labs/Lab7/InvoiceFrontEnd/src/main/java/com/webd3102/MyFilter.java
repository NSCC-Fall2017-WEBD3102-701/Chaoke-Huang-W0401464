package com.webd3102;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter("/*")
public class MyFilter implements Filter {

    Logger logger=Logger.getLogger(getClass().getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {

            HttpServletRequest reqt = (HttpServletRequest) servletRequest;
            // HttpServletResponse resp = (HttpServletResponse) servletResponse;
            //HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();
            if (reqURI.contains(".css") || reqURI.contains(".js"))
                logger.log(Level.WARNING, reqURI+" This is resource URI ");
            else {
                logger.log(Level.INFO, reqURI+" This is not resource URI ");
            }
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}
