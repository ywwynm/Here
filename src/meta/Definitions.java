package meta;

/**
 * Created by 张启 on 2016/4/14.
 * Definitions and constants.
 */
public class Definitions {

    private Definitions() {}

    public static abstract class Constants {
        public static final int PAGE_SIZE_BLOG = 16;
        public static final int PAGE_SIZE_MESSAGE = 24;

        public static final short BLOG_TYPE_ARTICLE = 0;
        public static final short BLOG_TYPE_IMAGE = 1;
        public static final short BLOG_TYPE_VIDEO = 2;

        public static final short BLOG_TYPE_MESSAGE = 3;

        public static final String COOKIE_USER_ID = "here_user_id";
        public static final String COOKIE_COOKIE_ID = "here_cookie_id";

        public static final String MEDIA_FILE_URL_ROOT = "media\\";
    }

    public static abstract class ServletUrls {
        public static final String MEDIA_FILE_HANDLER = "/media_file_handler";
    }

    public static abstract class PageUrls {
        public static final String ERROR = "/error.jsp";

        public static final String LOGIN = "/user/login.jsp";
        public static final String LOGIN_PROCESS = "/user/login_process.jsp";
        public static final String LOGOUT_PROCESS = "/user/logout_process.jsp";
        public static final String REGISTER = "/user/register.jsp";
        public static final String REGISTER_PROCESS = "/user/register_process.jsp";

        public static final String NAV_BAR = "/nav_bar.jsp";

        public static final String FRIENDS_MANAGEMENT
                = "/user/friends_management.jsp";
        public static final String UPDATE_FRIENDSHIP_PROCESS
                = "/user/update_friendship_process.jsp";

        public static final String FRIENDLY_LINK = "/friendly_link.jsp";
        public static final String FRIENDLY_LINKS_MANAGEMENT
                = "/user/friendly_links_management.jsp";
        public static final String CREATE_FRIENDLY_LINK
                = "/user/create_friendly_link.jsp";
        public static final String CREATE_FRIENDLY_LINK_PROCESS
                = "/user/create_friendly_link_process.jsp";
        public static final String DELETE_FRIENDLY_LINK_PROCESS
                = "/user/delete_friendly_link_process.jsp";

        public static final String INDEX = "/index.jsp";

        public static final String BLOG_INDEX = "/blog/blog_index.jsp";
        public static final String BLOG_DETAIL = "/blog/blog_detail.jsp";
        public static final String UPDATE_BLOG = "/blog/update_blog.jsp";
        public static final String UPDATE_BLOG_PROCESS
                = "/blog/update_blog_process.jsp";
        public static final String DELETE_BLOG_PROCESS
                = "/blog/delete_blog_process.jsp";

        public static final String MESSAGE_INDEX = "/message/message_index.jsp";
        public static final String UPDATE_MESSAGE = "/message/update_message.jsp";
        public static final String UPDATE_MESSAGE_PROCESS
                = "/message/update_message_process.jsp";
        public static final String DELETE_MESSAGE_PROCESS
                = "/message/delete_message_process.jsp";

        public static final String UPDATE_COMMENT = "/comment/update_comment.jsp";
        public static final String UPDATE_COMMENT_PROCESS
                = "/comment/update_comment_process.jsp";
        public static final String DELETE_COMMENT_PROCESS
                = "/comment/delete_comment_process.jsp";

        public static final String LIKE_BLOG_PROCESS
                = "/like/like_blog_process.jsp";
        public static final String LIKE_COMMENT_PROCESS
                = "/like/like_comment_process.jsp";

        public static final String SETTING_INDEX = "/user/setting_index.jsp";
        public static final String UPDATE_PASSWORD = "/user/update_password.jsp";
        public static final String UPDATE_PASSWORD_PROCESS
                = "/user/update_password_process.jsp";
    }

    public static abstract class TableUser {
        public static final String TABLE_USER = "user";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_CREATE_TIME = "create_time";
    }

    public static abstract class TableFriendship {
        public static final String TABLE_FRIENDSHIP = "friendship";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_FRIEND_ID = "friend_id";
        public static final String COLUMN_BUILD_UP_TIME = "build_up_time";
    }

    public static abstract class TableLoggedInUser {
        public static final String TABLE_LOGGED_IN_USER = "logged_in_user";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_COOKIE_ID = "cookie_id";
        public static final String COLUMN_CREATE_TIME = "create_time";
    }

    public static abstract class TableBlog {
        public static final String TABLE_BLOG = "blog";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_OWNER_ID = "owner_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_MEDIA_URL = "media_url";
        public static final String COLUMN_TAGS = "tags";
        public static final String COLUMN_CREATE_TIME = "create_time";
        public static final String COLUMN_UPDATE_TIME = "update_time";
        public static final String COLUMN_VISIT_TIMES = "visit_times";
    }

    public static abstract class TableComment {
        public static final String TABLE_COMMENT = "comment";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_OWNER_ID = "owner_id";
        public static final String COLUMN_BLOG_ID = "blog_id";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_CREATE_TIME = "create_time";
        public static final String COLUMN_UPDATE_TIME = "update_time";
    }

    public static abstract class TableMessage {
        public static final String TABLE_MESSAGE = "message";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_FROM_USER_ID = "from_user_id";
        public static final String COLUMN_TO_USER_ID = "to_user_id";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_CREATE_TIME = "create_time";
        public static final String COLUMN_UPDATE_TIME = "update_time";
    }

    public static abstract class TableUserLikesBlog {
        public static final String TABLE_USER_LIKES_BLOG = "user_likes_blog";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_BLOG_ID = "blog_id";
        public static final String COLUMN_LIKED_TIME = "liked_time";
    }

    public static abstract class TableUserLikesComment {
        public static final String TABLE_USER_LIKES_COMMENT = "user_likes_comment";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_COMMENT_ID = "comment_id";
        public static final String COLUMN_LIKED_TIME = "liked_time";
    }

    public static abstract class TableFriendlyLink {
        public static final String TABLE_FRIENDLY_LINK = "friendly_link";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_OWNER_ID = "owner_id";
        public static final String COLUMN_LINK_URL = "link_url";
    }

    public static abstract class Communication {
        public static final String USER = "user";
        public static final String USER_ID = "user_id";
        public static final String FRIEND_ID = "friend_id";

        public static final String REMEMBER_ME = "remember_me";

        public static final String PAGE_NO = "page_no";

        public static final String BLOG_ID = "blog_id";
        public static final String BLOG_TYPE = "blog_type";

        public static final String MESSAGE_ID = "message_id";
        public static final String TO_USER_ID = "to_user_id";

        public static final String COMMENT_ID = "comment_id";

        public static final String MEDIA_FILE = "media_file";
        public static final String MEDIA_URL = "media_url";

        public static final String ORIGINAL_PASSWORD = "original_password";
        public static final String NEW_PASSWORD = "new_password";
        public static final String NEW_PASSWORD_REPEAT = "new_password_repeat";

        public static final String FRIENDLY_LINK_ID = "friendly_link_id";
    }

}
