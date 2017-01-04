package database;

import bean.Message;
import meta.Definitions;
import util.StringUtil;

import static meta.Definitions.TableMessage.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 张启 on 2016/4/18.
 * dao for {@link Message}
 */
public class MessageDAO {

    private MessageDAO() {}

    public static Message getMessageById(long messageId) {
        String sql = "select * from " + TABLE_MESSAGE
                + " where " + COLUMN_ID + "=" + messageId;
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            rs.next();
            Message message = Message.newInstance(rs);
            return message;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

    public static ArrayList<Message> getMessages(long fromUserId, long toUserId) {
        String sql = "select * from " + TABLE_MESSAGE
                + " where " + COLUMN_FROM_USER_ID + "=" + fromUserId
                + " and " + COLUMN_TO_USER_ID + "=" + toUserId;
        return getMessages(sql);
    }

    public static ArrayList<Message> getMessagesByToUserId(
            long toUserId, int fromIndex, int pageSize) {
        String sql = "select * from " + TABLE_MESSAGE
                + " where " + COLUMN_TO_USER_ID + "=" + toUserId
                + " order by " + COLUMN_CREATE_TIME
                +" limit " + fromIndex + "," + pageSize;
        return getMessages(sql);
    }

    public static int getPageCount(long toUserId) {
        String sql = "select count(*) from " + TABLE_MESSAGE
                + " where " + COLUMN_TO_USER_ID + "=" + toUserId;
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            rs.next();
            int messageCount = rs.getInt(1);
            int pageSize = Definitions.Constants.PAGE_SIZE_MESSAGE;
            if (messageCount % pageSize != 0) {
                return messageCount / pageSize + 1;
            } else {
                return messageCount / pageSize;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

    public static boolean createMessage(Message message) {
        String content = StringUtil.escape(message.getContent());
        String sql = "insert into " + TABLE_MESSAGE + "("
                + COLUMN_FROM_USER_ID + ","
                + COLUMN_TO_USER_ID + ","
                + COLUMN_CONTENT + ","
                + COLUMN_CREATE_TIME + ","
                + COLUMN_UPDATE_TIME + ") values("
                + message.getFromUserId() + ","
                + message.getToUserId() + ",'"
                + content + "',"
                + System.currentTimeMillis() + ","
                + System.currentTimeMillis() + ")";
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    public static boolean updateMessageContent(Message updatedMessage) {
        String content = StringUtil.escape(updatedMessage.getContent());
        String sql = "update " + TABLE_MESSAGE + " set "
                + COLUMN_CONTENT + "='" + content + "',"
                + COLUMN_UPDATE_TIME + "=" + updatedMessage.getUpdateTime()
                + " where " + COLUMN_ID + "=" + updatedMessage.getId();
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    public static boolean deleteMessage(long messageId) {
        String sql = "delete from " + TABLE_MESSAGE
                + " where " + COLUMN_ID + "=" + messageId;
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    private static ArrayList<Message> getMessages(String sql) {
        ArrayList<Message> messages = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            while (rs.next()) {
                messages.add(Message.newInstance(rs));
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
            return messages;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

}
