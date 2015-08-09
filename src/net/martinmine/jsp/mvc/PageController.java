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

    /**
     * Sets the path to the view for the controller.
     * @param viewPath Path to the view for the controller.
     */
    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    /**
     * @return Path to the view associated with the controller.
     */
    public String getViewPath() {
        return viewPath;
    }

    /**
     * Default handler for all requests to the controller. This call is executed after all annotated methods.
     * @param servletRequest Incoming request.
     * @param servletResponse Outgoing response.
     */
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) { }

    /**
     * Redirects the request to a new page.
     * @param response Response to redirect on.
     * @param path Path to where to redirect the request.
     */
    protected void redirect(final HttpServletResponse response, final String path) {
        try {
            response.sendRedirect(path);
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
