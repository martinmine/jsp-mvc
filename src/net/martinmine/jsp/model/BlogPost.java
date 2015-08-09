package net.martinmine.jsp.model;

import java.sql.Date;

/**
 * Bean class for a specific post entry. Contains all relevant information for one post.
 */
public class BlogPost {
    private int id;
    private String title;
    private Date datePublished;
    private String author;
    private String header;
    private String content;

    public BlogPost() {
    }

    public BlogPost(int id, String title, Date datePublished, String author, String header, String content) {
        this.id = id;
        this.title = title;
        this.datePublished = datePublished;
        this.author = author;
        this.header = header;
        this.content = content;
    }

    public BlogPost(String title, String author, String header, String content) {
        this(0, title, new Date(System.currentTimeMillis()), author, header, content);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public String getAuthor() {
        return author;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }
}
