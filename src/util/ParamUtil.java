package util;

import meta.Definitions;

import static meta.Definitions.Communication.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * Created by 张启 on 2016/4/16.
 * utils to get parameters/attributes from {@link HttpServletRequest}
 */
public class ParamUtil {

    private ParamUtil() {}

    public static Long getUserId(HttpServletRequest request) {
        return parseLong(request.getParameter(USER_ID));
    }

    public static Boolean getRememberMe(HttpServletRequest request) {
        String param = request.getParameter(REMEMBER_ME);
        return param != null && "on".equals(param);
    }

    public static Long getFriendId(HttpServletRequest request) {
        return parseLong(request.getParameter(FRIEND_ID));
    }

    public static int getPageNo(HttpServletRequest request) {
        String pageNoStr = request.getParameter(PAGE_NO);
        if (pageNoStr == null || pageNoStr.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(pageNoStr);
        }
    }

    public static Long getMessageId(HttpServletRequest request) {
        return parseLong(request.getParameter(MESSAGE_ID));
    }

    public static Long getToUserId(HttpServletRequest request) {
        return parseLong(request.getParameter(TO_USER_ID));
    }

    public static Long getCommentId(HttpServletRequest request) {
        return parseLong(request.getParameter(COMMENT_ID));
    }

    public static Long getBlogId(HttpServletRequest request) {
        return parseLong(request.getParameter(BLOG_ID));
    }

    public static Short getBlogType(HttpServletRequest request) {
        return parseShort(request.getParameter(BLOG_TYPE));
    }

    public static Part getMediaFile(HttpServletRequest request) {
        try {
            return request.getPart(MEDIA_FILE);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMediaUrlAttr(HttpServletRequest request) {
        return (String) request.getAttribute(Definitions.Communication.MEDIA_URL);
    }

    public static Long getFriendlyLinkId(HttpServletRequest request) {
        return parseLong(request.getParameter(FRIENDLY_LINK_ID));
    }

    private static Long parseLong(String param) {
        try {
            return Long.parseLong(param);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Short parseShort(String param) {
        try {
            return Short.parseShort(param);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
