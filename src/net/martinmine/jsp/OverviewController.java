package net.martinmine.jsp;

import net.martinmine.jsp.model.PostFactory;
import net.martinmine.jsp.mvc.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for viewing the overview of all the posts.
 */
public class OverviewController extends PageController {

    /**
     * Retrieves all the posts stored in the database to the view.
     * @param servletRequest Request.
     * @param servletResponse Response.
     */
    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        servletRequest.setAttribute("posts", PostFactory.getInstance().getAllPosts());
    }
}
