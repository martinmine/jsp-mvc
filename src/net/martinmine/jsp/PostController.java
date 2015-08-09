package net.martinmine.jsp;

import net.martinmine.jsp.model.BlogPost;
import net.martinmine.jsp.model.PostFactory;
import net.martinmine.jsp.mvc.PageController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for viewing one post.
 */
public class PostController extends PageController {

    /**
     * Retrieves the post that the user is requesting specified by the id parameter.
     * If the post does not exist, the user will be redirected to the 404 not found page.
     * @param servletRequest Request.
     * @param servletResponse Response.
     */
    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        final int articleId = Integer.valueOf(servletRequest.getParameter("id"));
        final BlogPost post = PostFactory.getInstance().getPost(articleId);

        if (post == null) {
            redirect(servletResponse, "/404.jsp");
        } else {
            servletRequest.setAttribute("post", post);
        }
    }
}
