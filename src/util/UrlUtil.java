package util;

import static meta.Definitions.Constants.*;
import static meta.Definitions.PageUrls.*;
import static meta.Definitions.Communication.*;
import static meta.Definitions.ServletUrls.*;

/**
 * Created by 张启 on 2016/4/16.
 * utils to get appropriate urls.
 */
public class UrlUtil {

    private UrlUtil() {}

    public static String getIndexUrl(long userId) {
        return getIndexUrl(userId, BLOG_TYPE_ARTICLE, 0);
    }

    public static String getIndexUrl(long userId, short blogType) {
        return getIndexUrl(userId, blogType, 0);
    }

    public static String getIndexUrl(long userId, int pageNo) {
        return getIndexUrl(userId, BLOG_TYPE_ARTICLE, pageNo);
    }

    public static String getIndexUrl(long userId, short blogType, int pageNo) {
        return INDEX
                + "?" + USER_ID + "=" + userId
                + "&" + BLOG_TYPE + "=" + blogType
                + "&" + PAGE_NO + "=" + pageNo;
    }

    public static String getBlogDetailUrl(long blogId) {
        return BLOG_DETAIL
                + "?" + BLOG_ID + "=" + blogId;
    }

    public static String getCreateBlogUrl(short blogType) {
        return UPDATE_BLOG
                + "?" + BLOG_ID + "=-1&"
                + BLOG_TYPE + "=" + blogType;
    }

    public static String getUpdateBlogProcessUrl(long blogId, short blogType) {
        String url = MEDIA_FILE_HANDLER
                + "?" + BLOG_ID + "=" + blogId;
        if (blogId == -1) {
            url += "&" + BLOG_TYPE + "=" + blogType;
        }
        return url;
    }

    public static String getUpdateMessageUrl(long messageId, long toUserId) {
        return UPDATE_MESSAGE
                + getMessageUrlPostfix(messageId, toUserId);
    }

    public static String getUpdateMessageProcessUrl(long messageId, long toUserId) {
        return UPDATE_MESSAGE_PROCESS
                + getMessageUrlPostfix(messageId, toUserId);
    }

    public static String getDeleteMessageProcessUrl(long messageId, long toUserId) {
        return DELETE_MESSAGE_PROCESS
                + getMessageUrlPostfix(messageId, toUserId);
    }

    public static String getUpdateCommentUrl(
            long commentId, long blogId) {
        return UPDATE_COMMENT
                + getCommentUrlPostfix(commentId, blogId);
    }

    public static String getUpdateCommentProcessUrl(
            long commentId, long blogId) {
        return UPDATE_COMMENT_PROCESS
                + getCommentUrlPostfix(commentId, blogId);
    }

    public static String getDeleteCommentProcessUrl(long commentId) {
        return DELETE_COMMENT_PROCESS
                + "?" + COMMENT_ID + "=" + commentId;
    }

    public static String getLikeBlogUrl(long blogId) {
        return LIKE_BLOG_PROCESS + "?"
                + BLOG_ID + "=" + blogId;
    }

    public static String getUpdateFriendshipProcessUrl(long friendId) {
        return UPDATE_FRIENDSHIP_PROCESS
                + "?" + FRIEND_ID + "=" + friendId;
    }

    public static String getFriendlyLinkUrl(long ownerId) {
        return FRIENDLY_LINK + "?" + USER_ID + "=" + ownerId;
    }

    public static String getDeleteFriendlyLinkProcessUrl(long friendlyLinkId) {
        return DELETE_FRIENDLY_LINK_PROCESS
                + "?" + FRIENDLY_LINK_ID + "=" + friendlyLinkId;
    }

    private static String getMessageUrlPostfix(long messageId, long toUserId) {
        String postfix = "?" + MESSAGE_ID + "=" + messageId;
        if (messageId == -1) {
            postfix += "&" + TO_USER_ID + "=" + toUserId;
        }
        return postfix;
    }

    private static String getCommentUrlPostfix(long commentId, long blogId) {
        String postfix = "?" + COMMENT_ID + "=" + commentId;
        if (commentId == -1) {
            postfix += "&" + BLOG_ID + "=" + blogId;
        }
        return postfix;
    }

}
