package database;

import bean.Comment;
import util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static meta.Definitions.TableComment.*;
import static meta.Definitions.TableUserLikesComment.*;

/**
 * Created by 张启 on 2016/4/15.
 * dao for {@link Comment}
 */
public class CommentDAO {

    private CommentDAO() {}

    public static Comment getCommentById(long id) {
        String sql = "select * from " + TABLE_COMMENT
                + " where " + COLUMN_ID + "=" + id;
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            rs.next();
            Comment comment = Comment.newInstance(rs);
            return comment;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

    public static ArrayList<Comment> getComments(long blogId) {
        ArrayList<Comment> comments = new ArrayList<>();
        String sql = "select * from " + TABLE_COMMENT
                + " where " + COLUMN_BLOG_ID + "=" + blogId;
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                comments.add(Comment.newInstance(rs));
            }
            sort(comments);
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
            return comments;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

    public static int getLikedTimes(long commentId) {
        String sql = "select count(*) from " + TABLE_USER_LIKES_COMMENT
                + " where " + COLUMN_COMMENT_ID + "=" + commentId;
        return DatabaseHelper.executeQueryForCount(sql);
    }

    public static boolean createComment(Comment comment) {
        String content = StringUtil.escape(comment.getContent());
        String sql = "insert into " + TABLE_COMMENT + "("
                + COLUMN_BLOG_ID + ","
                + COLUMN_OWNER_ID + ","
                + COLUMN_CONTENT + ","
                + COLUMN_CREATE_TIME + ","
                + COLUMN_UPDATE_TIME + ") values ("
                + comment.getBlogId() + ","
                + comment.getOwnerId() + ",'"
                + content + "',"
                + System.currentTimeMillis() + ","
                + System.currentTimeMillis() + ")";
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    public static boolean updateCommentContent(Comment updatedComment) {
        String content = StringUtil.escape(updatedComment.getContent());
        String sql = "update " + TABLE_COMMENT + " set "
                + COLUMN_CONTENT + "='" + content + "',"
                + COLUMN_UPDATE_TIME + "=" + updatedComment.getUpdateTime()
                + " where " + COLUMN_ID + "=" + updatedComment.getId();
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    public static boolean deleteComment(long commentId) {
        String sql = "delete from " + TABLE_COMMENT
                + " where " + COLUMN_ID + "=" + commentId;
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    private static void sort(ArrayList<Comment> comments) {
        comments.sort((c1, c2) -> {
            int l1 = CommentDAO.getLikedTimes(c1.getId());
            int l2 = CommentDAO.getLikedTimes(c2.getId());
            if (l1 < l2) {
                return 1;
            } else if (l1 > l2) {
                return -1;
            } else {
                long t1 = c1.getCreateTime();
                long t2 = c2.getCreateTime();
                if (t1 < t2) {
                    return 1;
                } else if (t1 > t2) {
                    return -1;
                } else return 0;
            }
        });
    }

}
