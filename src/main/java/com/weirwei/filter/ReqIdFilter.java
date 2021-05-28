package com.weirwei.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * @author weirwei 2021/5/28 15:42
 */
@WebFilter(filterName = "ReqIdFilter")
public class ReqIdFilter implements Filter  {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain filterChain) throws IOException, ServletException {
        req.setAttribute("reqId", UUID.randomUUID().toString());
        filterChain.doFilter(req, rsp);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
