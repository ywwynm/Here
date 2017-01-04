package bean;

import database.DatabaseHelper;
import database.UserDAO;
import meta.Definitions;

import static meta.Definitions.TableUser.*;
import static meta.Definitions.TableUserLikesBlog.*;
import static meta.Definitions.TableFriendship.COLUMN_FRIEND_ID;
import static meta.Definitions.TableFriendship.TABLE_FRIENDSHIP;
import static meta.Definitions.TableUserLikesComment.COLUMN_COMMENT_ID;
import static meta.Definitions.TableUserLikesComment.TABLE_USER_LIKES_COMMENT;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 张启 on 2016/4/14.
 * User bean
 */
public class User {

    private long id;
    private String email;
    private String username;
    private String password;
    private long createTime;

    public User() {}

    public User(long id, String email, String username, String password,
                long createTime) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
    }

    public static User newInstance(ResultSet rs) throws SQLException {
        long id = rs.getLong(COLUMN_ID);
        String email = rs.getString(COLUMN_EMAIL);
        String username = rs.getString(COLUMN_USERNAME);
        String password = rs.getString(COLUMN_PASSWORD);
        long createTime = rs.getLong(COLUMN_CREATE_TIME);
        return new User(id, email, username, password, createTime);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    /**
     * Get user's friends.
     * @return user's friends.
     */
    public ArrayList<User> getFriends() {
        ArrayList<User> friends = new ArrayList<>();
        String sql = "select " + Definitions.TableFriendship.COLUMN_FRIEND_ID
                + " from " + Definitions.TableFriendship.TABLE_FRIENDSHIP
                + " where " + Definitions.TableFriendship.COLUMN_USER_ID + "=" + id;
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                long id = rs.getLong(Definitions.TableFriendship.COLUMN_FRIEND_ID);
                friends.add(UserDAO.getUserById(id));
            }
            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
            return friends;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

    /**
     * Check if user has a friend whose id is {@param friendId}
     * @param friendId friend's id
     * @return {@code true} if user has this friend. {@code false} otherwise.
     */
    public boolean hasFriend(long friendId) {
        String sql = "select count(*) from " + TABLE_FRIENDSHIP
                + " where " + COLUMN_USER_ID + "=" + id
                + " and " + COLUMN_FRIEND_ID + "=" + friendId;
        return DatabaseHelper.executeQueryForCount(sql) == 1;
    }

    /**
     * Make friend with another user.
     * @param friendId friend's id.
     * @return {@code true} if friendship is built up. {@code false} otherwise.
     */
    public boolean makeFriendWith(long friendId) {
        if (friendId == id) {
            return false;
        }
        String sql = "insert into " + TABLE_FRIENDSHIP + " values("
                + id + ","
                + friendId + ","
                + System.currentTimeMillis() + ")";
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    /**
     * Break with another user.
     * @param friendId friend's id.
     * @return {@code true} if friendship is over. {@code false} otherwise.
     */
    public boolean breakWith(long friendId) {
        String sql = "delete from " + TABLE_FRIENDSHIP
                + " where " + COLUMN_USER_ID + "=" + id
                + " and " + COLUMN_FRIEND_ID + "=" + friendId;
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    /**
     * Check if user has liked a blog with given blogId.
     * @param blogId blog's id
     * @return {@code true} if user liked that blog before. {@code false} otherwise.
     */
    public boolean hasLikedBlog(long blogId) {
        String sql = "select count(*) from " + TABLE_USER_LIKES_BLOG
                + " where " + COLUMN_USER_ID + "=" + id
                + " and " + COLUMN_BLOG_ID + "=" + blogId;
        return DatabaseHelper.executeQueryForCount(sql) == 1;
    }

    /**
     * like a blog.
     * @param blogId blog's id.
     * @return {@code true} if operation finishes successfully.
     *          {@code false} otherwise.
     */
    public boolean likeBlog(long blogId) {
        String sql = "insert into " + TABLE_USER_LIKES_BLOG
                + " values("
                + id + ","
                + blogId + ","
                + System.currentTimeMillis() + ")";
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    /**
     * cancel liking a blog.
     * @param blogId blog's id.
     * @return {@code true} if operation finishes successfully.
     *          {@code false} otherwise.
     */
    public boolean cancelLikeBlog(long blogId) {
        String sql = "delete from " + TABLE_USER_LIKES_BLOG
                + " where " + COLUMN_USER_ID + "=" + id
                + " and " + COLUMN_BLOG_ID + "=" + blogId;
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    /**
     * Check if user has liked a comment with given commentId.
     * @param commentId comment's id
     * @return {@code true} if user liked that comment before.
     *          {@code false} otherwise.
     */
    public boolean hasLikedComment(long commentId) {
        String sql = "select count(*) from " + TABLE_USER_LIKES_COMMENT
                + " where " + COLUMN_USER_ID + "=" + id
                + " and " + COLUMN_COMMENT_ID + "=" + commentId;
        return DatabaseHelper.executeQueryForCount(sql) == 1;
    }

    /**
     * like a comment.
     * @param commentId comment's id.
     * @return {@code true} if operation finishes successfully.
     *          {@code false} otherwise.
     */
    public boolean likeComment(long commentId) {
        String sql = "insert into " + TABLE_USER_LIKES_COMMENT + " values("
                + id + ","
                + commentId + ","
                + System.currentTimeMillis() + ")";
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    /**
     * cancel liking a comment.
     * @param commentId comment's id.
     * @return {@code true} if operation finishes successfully.
     *          {@code false} otherwise.
     */
    public boolean cancelLikeComment(long commentId) {
        String sql = "delete from " + TABLE_USER_LIKES_COMMENT
                + " where " + COLUMN_USER_ID + "=" + id
                + " and " + COLUMN_COMMENT_ID + "=" + commentId;
        return DatabaseHelper.executeSingleUpdate(sql);
    }
}
