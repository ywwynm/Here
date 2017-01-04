package database;

import bean.Blog;
import meta.Definitions.*;
import util.StringUtil;

import static meta.Definitions.TableBlog.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 张启 on 2016/4/14.
 * dao for {@link Blog}
 */
public class BlogDAO {

    public static Blog getBlogById(long id) {
        String sql = "select * from " + TABLE_BLOG
                + " where " + COLUMN_ID + "=" + id;
        return getBlogBySql(sql);
    }

    public static Blog getBlogByCommentId(long commentId) {
        String sql = "select " + TableComment.COLUMN_BLOG_ID
                + " from " + TableComment.TABLE_COMMENT
                + " where " + TableComment.COLUMN_ID + "=" + commentId;
        ResultSet rs = null;
        long blogId = -1;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            rs.next();
            blogId = rs.getLong(TableComment.COLUMN_BLOG_ID);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DatabaseHelper.release(rs);
        }

        return getBlogById(blogId);
    }

    public static ArrayList<Blog> getBlogsByOwnerId(
            long ownerId, short blogType, int fromIndex, int pageSize) {
        String sql = "select * from " + TABLE_BLOG
                + " where " + COLUMN_OWNER_ID + "=" + ownerId
                + " and " + COLUMN_TYPE + "=" + blogType
                + " order by " + COLUMN_CREATE_TIME + " desc"
                + " limit " + fromIndex + "," + pageSize;
        return getBlogs(sql);
    }

    public static boolean createBlog(Blog blog) {
        String title = StringUtil.escape(blog.getTitle());
        String content = StringUtil.escape(blog.getContent());
        String mediaUrl = StringUtil.escape(blog.getMediaUrl());
        String sql = "insert into " + TABLE_BLOG + "("
                + COLUMN_TYPE + ","
                + COLUMN_OWNER_ID + ","
                + COLUMN_TITLE + ","
                + COLUMN_CONTENT + ","
                + COLUMN_MEDIA_URL + ","
                + COLUMN_TAGS + ","
                + COLUMN_CREATE_TIME + ","
                + COLUMN_UPDATE_TIME + ","
                + COLUMN_VISIT_TIMES + ") values ("
                + blog.getType() + ","
                + blog.getOwnerId() + ", '"
                + title + "', '"
                + content + "', '"
                + mediaUrl + "', '"
                + blog.getTags() + "', "
                + System.currentTimeMillis() + ","
                + System.currentTimeMillis() + ", 0)";
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    public static boolean updateBlogContents(Blog updatedBlog) {
        String title = StringUtil.escape(updatedBlog.getTitle());
        String content = StringUtil.escape(updatedBlog.getContent());
        String mediaUrl = StringUtil.escape(updatedBlog.getMediaUrl());
        String sql = "update " + TABLE_BLOG + " set "
                + COLUMN_TITLE + "='" + title + "',"
                + COLUMN_CONTENT + "='" + content + "',"
                + COLUMN_MEDIA_URL + "='" + mediaUrl + "',"
                + COLUMN_TAGS + "='" + updatedBlog.getTags() + "',"
                + COLUMN_UPDATE_TIME + "=" + updatedBlog.getUpdateTime()
                + " where " + COLUMN_ID + "=" + updatedBlog.getId();
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    public static boolean deleteBlog(long id) {
        String sql0 = "delete from " + TABLE_BLOG
                + " where " + COLUMN_ID + "=" + id;
        String sql1 = "delete from " + TableComment.TABLE_COMMENT
                + " where " + TableComment.COLUMN_BLOG_ID + "=" + id;
        String sql2 = "delete from " + TableUserLikesBlog.TABLE_USER_LIKES_BLOG
                + " where " + TableUserLikesBlog.COLUMN_BLOG_ID + "=" + id;
        return DatabaseHelper.executeTransactionForUpdate(sql0, sql1, sql2);
    }

    public static int getPageCount(long ownerId, short type) {
        String sql = "select count(*) from " + TABLE_BLOG
                + " where " + COLUMN_OWNER_ID + "=" + ownerId
                + " and " + COLUMN_TYPE + "=" + type;
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            rs.next();
            int articleCount = rs.getInt(1);
            int pageSize = Constants.PAGE_SIZE_BLOG;
            if (articleCount % pageSize != 0) {
                return articleCount / pageSize + 1;
            } else {
                return articleCount / pageSize;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

    public static int getLikedTimes(long id) {
        String sql = "select count(*) from " + TableUserLikesBlog.TABLE_USER_LIKES_BLOG
                + " where " + TableUserLikesBlog.COLUMN_BLOG_ID + "=" + id;
        return DatabaseHelper.executeQueryForCount(sql);
    }

    public static boolean updateVisitTimes(long id) {
        String sql = "update " + TABLE_BLOG + " set "
                + COLUMN_VISIT_TIMES + "=" + COLUMN_VISIT_TIMES + "+1"
                + " where " + COLUMN_ID + "=" + id;
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    private static Blog getBlogBySql(String sql) {
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            rs.next();
            Blog blog = Blog.newInstance(rs);
            return blog;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

    private static ArrayList<Blog> getBlogs(String sql) {
        ArrayList<Blog> blogs = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                blogs.add(Blog.newInstance(rs));
            }
            return blogs;
        } catch (SQLException e) {
            e.printStackTrace();
            return blogs;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

}
