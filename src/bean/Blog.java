package bean;

import meta.Definitions;
import util.StringUtil;

import static meta.Definitions.TableBlog.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 张启 on 2016/4/14.
 * blog bean
 */
public class Blog {

    private long id;
    private short type;
    private long ownerId;
    private String title;
    private String content;
    private String mediaUrl;
    private String tags;
    private long createTime;
    private long updateTime;
    private int visitTimes;

    public Blog() {}

    public Blog(long id, short type, long ownerId, String title, String content,
                String mediaUrl, String tags, long createTime, long updateTime,
                int visitTimes) {
        this.id = id;
        this.type = type;
        this.ownerId = ownerId;
        this.title = title;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.tags = tags;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.visitTimes = visitTimes;
    }

    public static Blog newInstance(ResultSet rs) {
        try {
            long id = rs.getLong(COLUMN_ID);
            short type = rs.getShort(COLUMN_TYPE);
            long ownerId = rs.getLong(COLUMN_OWNER_ID);
            String title = rs.getString(COLUMN_TITLE);
            String content = rs.getString(COLUMN_CONTENT);
            String mediaUrl = rs.getString(COLUMN_MEDIA_URL);
            String tags = rs.getString(COLUMN_TAGS);
            long createTime = rs.getLong(COLUMN_CREATE_TIME);
            long updateTime = rs.getLong(COLUMN_UPDATE_TIME);
            int visitTimes = rs.getInt(COLUMN_VISIT_TIMES);
            return new Blog(id, type, ownerId, title, content,
                    mediaUrl, tags, createTime, updateTime, visitTimes);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(int visitTimes) {
        this.visitTimes = visitTimes;
    }

    public String getRealMediaUrl() {
        return "\\" + Definitions.Constants.MEDIA_FILE_URL_ROOT
                + StringUtil.unescape(mediaUrl);
    }

    public static String getTypeDescription(short blogType) {
        switch (blogType) {
            case Definitions.Constants.BLOG_TYPE_ARTICLE:
                return "文章";
            case Definitions.Constants.BLOG_TYPE_IMAGE:
                return "图片";
            case Definitions.Constants.BLOG_TYPE_VIDEO:
                return "视频";
            case Definitions.Constants.BLOG_TYPE_MESSAGE:
                return "留言";
        }
        return null;
    }

    public static boolean isMediaType(short blogType) {
        return blogType == Definitions.Constants.BLOG_TYPE_IMAGE
                || blogType == Definitions.Constants.BLOG_TYPE_VIDEO;
    }

    public static String getMIMEType(short type) {
        if (type == Definitions.Constants.BLOG_TYPE_IMAGE) {
            return "image/*";
        } else if (type == Definitions.Constants.BLOG_TYPE_VIDEO) {
            return "video/*";
        }
        return null;
    }

}
