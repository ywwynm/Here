package util;

import bean.User;
import database.DatabaseHelper;
import database.UserDAO;
import meta.Definitions;
import static meta.Definitions.TableLoggedInUser.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 张启 on 2016/4/18.
 * utils for cookie and logged in info
 */
public class LoggedInUtil {

    static int TWO_WEEK_SECONDS = 60 * 60 * 24 * 14;

    public static User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Definitions.Communication.USER);
        if (user != null) {
            return user;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        long userId = -1;
        String cookieId = null;
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (Definitions.Constants.COOKIE_USER_ID.equals(name)) {
                userId = Long.parseLong(cookie.getValue());
                if (cookieId != null) break;
            }
            if (Definitions.Constants.COOKIE_COOKIE_ID.equals(name)) {
                cookieId = cookie.getValue();
                if (userId != -1) break;
            }
        }
        return getLoggedInUser(userId, cookieId, session);
    }

    private static User getLoggedInUser(
            long userId, String cookieId, HttpSession session) {
        String sql = "select * from " + TABLE_LOGGED_IN_USER
                + " where " + COLUMN_USER_ID + "=" + userId;
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.executeQuery(sql);
            rs.next();
            long createTime = rs.getLong(COLUMN_CREATE_TIME);
            String dbCookieId = rs.getString(COLUMN_COOKIE_ID);
            if (System.currentTimeMillis() - createTime > TWO_WEEK_SECONDS * 1000
                    || !cookieId.equals(dbCookieId)) {
                deleteLoggedInInfo(userId);
                return null;
            }

            User user = UserDAO.getUserById(userId);
            if (user != null) {
                session.setAttribute(Definitions.Communication.USER, user);
            }
            return user;
        } catch (SQLException e) {
            return null;
        } finally {
            DatabaseHelper.release(rs);
        }
    }

    public static boolean addUserCookie(long userId, HttpServletResponse response) {
        Cookie userCookie = new Cookie(
                Definitions.Constants.COOKIE_USER_ID, String.valueOf(userId));

        String cookieId = generateRandomString(128);
        Cookie cookieCookie = new Cookie(
                Definitions.Constants.COOKIE_COOKIE_ID, cookieId);

        userCookie.setMaxAge(TWO_WEEK_SECONDS);
        cookieCookie.setMaxAge(TWO_WEEK_SECONDS);

        userCookie.setPath("/");
        cookieCookie.setPath("/");

        response.addCookie(userCookie);
        response.addCookie(cookieCookie);

        return addLoggedInInfo(userId, cookieId);
    }

    public static boolean deleteUserCookie(
            long userId, HttpSession session, HttpServletResponse response) {
        session.removeAttribute(Definitions.Communication.USER);

        Cookie userCookie = new Cookie(
                Definitions.Constants.COOKIE_USER_ID, null);
        Cookie cookieCookie = new Cookie(
                Definitions.Constants.COOKIE_COOKIE_ID, null);

        userCookie.setMaxAge(0);
        cookieCookie.setMaxAge(0);

        response.addCookie(userCookie);
        response.addCookie(cookieCookie);

        return deleteLoggedInInfo(userId);
    }

    private static String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        // String that I loved.
        String alphaNumeric =
                "0123456789abcdefghijklmnopqrstuvwxyzABCCDEFGHIJJKLMNOPQQRSTUVWXYZ";
        int bound = alphaNumeric.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(alphaNumeric.charAt(secureRandom.nextInt(bound)));
        }
        return sb.toString();
    }

    private static boolean addLoggedInInfo(long userId, String cookieId) {
        String sql = "insert into " + TABLE_LOGGED_IN_USER + " values("
                + userId + ",'"
                + cookieId + "',"
                + System.currentTimeMillis() + ")";
        return DatabaseHelper.executeSingleUpdate(sql);
    }

    private static boolean deleteLoggedInInfo(long userId) {
        String sql = "delete from " + TABLE_LOGGED_IN_USER
                + " where " + COLUMN_USER_ID + "=" + userId;
        return DatabaseHelper.executeSingleUpdate(sql);
    }

}
