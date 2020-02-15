package ua.org.workshop.filter;

import ua.org.workshop.configuration.ApplicationConstants;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter(ApplicationConstants.InitParameters.APP_REQUEST_ENCODING_PARAMETER);
        if (encoding == null) encoding = ApplicationConstants.InitParameters.APP_ENCODING;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        next.doFilter(request, response);
    }

    public void destroy() {
    }
}
