package net.martinmine.jsp.model;

import net.martinmine.jsp.DatabaseManager;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Factory class for retrieving posts to/from the database.
 * This class has hard-coded db credentials for the sake of simplicity.
 */
public class PostFactory {
    private static final PostFactory INSTANCE = new PostFactory();
    private final DatabaseManager databaseManager;

    /**
     * @return Singleton instance for the post factory.
     */
    public static PostFactory getInstance() {
        return INSTANCE;
    }

    private PostFactory() {
        this.databaseManager = new DatabaseManager("root", "lol123", "posts", "localhost", 3306);
    }

    /**
     * Gets a post from the database.
     * @param postId Id of the post.
     * @return The post if found, otherwise {@code null}.
     */
    public BlogPost getPost(final int postId) {
        try (Connection con = databaseManager.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT id, title, datePublished, author, header, content " +
                    "FROM post WHERE id = ?")) {

            stmt.setInt(1, postId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return fromRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BlogPost fromRow(final ResultSet resultSet) throws SQLException {
        return new BlogPost(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getDate("datePublished"),
                resultSet.getString("author"), resultSet.getString("header"), resultSet.getString("content"));
    }

    /**
     * @return List of all stored posts, ordered by id descending.
     */
    public List<BlogPost> getAllPosts() {
        try (Connection con = databaseManager.getConnection();
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT id, title, datePublished, author, header, content " +
                    "FROM post ORDER BY id DESC")) {

            final List<BlogPost> posts = new LinkedList<>();
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    posts.add(fromRow(rs));
                }
            }

            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Makes a post persistent in the database.
     * @param post Post to save to database.
     */
    public void storePost(final BlogPost post) {
        try (Connection con = databaseManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                     "INSERT INTO post (title, datePublished, author, header, content) "
                     + "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, post.getTitle());
            stmt.setDate(2, post.getDatePublished());
            stmt.setString(3, post.getAuthor());
            stmt.setString(4, post.getHeader());
            stmt.setString(5, post.getContent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
