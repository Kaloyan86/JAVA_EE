package app.web.filters;

import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/views/index.jsf", "/views/register.jsf", "/views/login.jsf"})
public class Authorization implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String id = (String) ((HttpServletRequest) request).getSession().getAttribute("userId");

        if (id != null) {
            ((HttpServletResponse) response).sendRedirect("/views/home.jsf");
            return;
        }
        chain.doFilter(request, response);
    }
}
