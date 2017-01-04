package bean;

import static meta.Definitions.TableComment.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 张启 on 2016/4/15.
 * comment bean
 */
public class Comment {

    private long id;
    private long blogId;
    private long ownerId;
    private String content;
    private long createTime;
    private long updateTime;

    public Comment() {}

    public Comment(long id, long blogId, long ownerId, String content,
                   long createTime, long updateTime) {
        this.id = id;
        this.blogId = blogId;
        this.ownerId = ownerId;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public static Comment newInstance(ResultSet rs) {
        try {
            long id = rs.getLong(COLUMN_ID);
            long blogId = rs.getLong(COLUMN_BLOG_ID);
            long ownerId = rs.getLong(COLUMN_OWNER_ID);
            String content = rs.getString(COLUMN_CONTENT);
            long createTime = rs.getLong(COLUMN_CREATE_TIME);
            long updateTime = rs.getLong(COLUMN_UPDATE_TIME);
            return new Comment(id, blogId, ownerId, content,
                    createTime, updateTime);
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

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
