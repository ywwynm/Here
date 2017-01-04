package database;

import bean.User;
import meta.Definitions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static meta.Definitions.TableUser.*;

/**
 * Created by 张启 on 2016/4/14.
 * dao for {@link User}
 */
public class UserDAO {

    public static User getUserById(long id) {
        String sql = "select * from " + TABLE_USER
                + " where " + COLUMN_ID + "=" + id;
        return getUser(sql);
    }

    public static User getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }
        String sql = "select * from " + TABLE_USER
                + " where " + COLUMN_EMAIL + "='" + email + "'";
        return getUser(sql);
    }

    public static User register(String email, String username, String password) {
        String sql = "insert into " + TABLE_USER + "("
                + COLUMN_EMAIL + ","
                + COLUMN_USERNAME + ","
                + COLUMN_PASSWORD + ","
                + COLUMN_CREATE_TIME + ") values ('"
                + email + "','"
                + username + "','"
                + password + "',"
                + System.currentTimeMillis() + ")";
        boolean registered = DatabaseHelper.executeSingleUpdate(sql);
        return registered ? getUserByEmail(email) : null;
    }

    public static User login(String email, String password) {
        User user = getUserByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        } else return user;
    }

    public static boolean updatePassword(long userId, String newPassword) {
        String sql = "update " + TABLE_USER + " set "
                + COLUMN_PASSWORD + "='" + newPassword
                + "' where " + COLUMN_ID + "=" + userId;
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    private static User getUser(String sql) {
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            rs.next();
            User user = User.newInstance(rs);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

}
