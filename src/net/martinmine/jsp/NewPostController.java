package net.martinmine.jsp;

import net.martinmine.jsp.model.BlogPost;
import net.martinmine.jsp.model.PostFactory;
import net.martinmine.jsp.mvc.PageController;
import net.martinmine.jsp.mvc.annotations.Post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for creating new posts.
 */
public class NewPostController extends PageController {

    /**
     * Stores a new blog post to the database.
     * This function should probably do some validation on the received call.
     * @param servletRequest Request.
     * @param servletResponse Response.
     */
    @Post
    public void invoke(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse) {
        final String author = servletRequest.getParameter("author");
        final String title = servletRequest.getParameter("title");
        final String header = servletRequest.getParameter("header");
        final String content = servletRequest.getParameter("content");

        final BlogPost post = new BlogPost(title, author, header, content);
        PostFactory.getInstance().storePost(post);

        redirect(servletResponse, "/overview");
    }
}

