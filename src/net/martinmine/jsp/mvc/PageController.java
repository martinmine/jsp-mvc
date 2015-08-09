package net.martinmine.jsp.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Each class implementing this class acts as the controller to a page.
 * This class provides common tasks for each controller, such as redirection and default request handling.
 */
public abstract class PageController {
    private String viewPath;

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public String getViewPath() {
        return viewPath;
    }

    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) { }

    protected void redirect(final HttpServletResponse response, final String path) {
        try {
            response.sendRedirect(path);
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
