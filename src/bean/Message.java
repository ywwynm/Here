package bean;

import static meta.Definitions.TableMessage.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 张启 on 2016/4/18.
 * message bean
 */
public class Message {

    private long id;
    private long fromUserId;
    private long toUserId;
    private String content;
    private long createTime;
    private long updateTime;

    public Message() {}

    public Message(long id, long fromUserId, long toUserId,
                   String content, long createTime, long updateTime) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public static Message newInstance(ResultSet rs) {
        try {
            long id = rs.getLong(COLUMN_ID);
            long fromUserId = rs.getLong(COLUMN_FROM_USER_ID);
            long toUserId = rs.getLong(COLUMN_TO_USER_ID);
            String content = rs.getString(COLUMN_CONTENT);
            long createTime = rs.getLong(COLUMN_CREATE_TIME);
            long updateTime = rs.getLong(COLUMN_UPDATE_TIME);
            return new Message(id, fromUserId, toUserId, content,
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

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
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
