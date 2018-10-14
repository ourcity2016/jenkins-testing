package loan.ppcat.godsky.controller.filter;

import loan.ppcat.godsky.controller.util.InputStreamUtil;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoginFilter implements Filter {
    private Properties properties = new Properties();
    private Logger logger = Logger.getLogger(LoginFilter.class);
    private String usernameConfig = "";
    private String passwordConfig = "";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        InputStream inputStream = null;

        try {
            inputStream = LoginFilter.class.getResourceAsStream("/server.properties");
            properties.load(inputStream);
            usernameConfig = properties.getProperty("username");
            passwordConfig = properties.getProperty("password");
        } catch (IOException ex) {
            logger.error("User properties not config , please check !", ex);
        } finally {
            InputStreamUtil.closeInputStream(inputStream);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpServletRequest.getSession();
        String url = httpServletRequest.getServletPath();
        Object username = session.getAttribute("username");
        if (url.equals("/login") || (url.startsWith("/resources/") && !url.startsWith("/resources/app")) || (username != null && username.toString().equals(usernameConfig))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpServletResponse.sendRedirect("/login");
        }

    }

    @Override
    public void destroy() {

    }
}
