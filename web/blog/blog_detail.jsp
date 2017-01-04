<%@ page import="meta.Definitions.*" %>
<%@ page import="bean.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.Comment" %>
<%@ page import="database.CommentDAO" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="database.BlogDAO" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="bean.Blog" %>
<%@ page import="util.StringUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/15
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <%
        User curUser = LoggedInUtil.getLoggedInUser(request);
        Long blogId = ParamUtil.getBlogId(request);
        Blog blog = BlogDAO.getBlogById(blogId);
        short blogType = blog.getType();
    %>
    <title><%=StringUtil.unescape(blog.getTitle())%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- 引入 Bootstrap -->
    <link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- 包括所有已编译的插件 -->
    <script src="js/bootstrap.min.js"></script>
</head>
<body background="../images/wood_pattern.jpg" style="text-align: center;">

<jsp:include page="<%=cp + PageUrls.NAV_BAR%>">
    <jsp:param name="user_id" value="<%=blog.getOwnerId()%>"/>
</jsp:include>

    <%=StringUtil.unescape(blog.getContent())%>
    <br/>
    <br/>

    <%
        if (blogType == Constants.BLOG_TYPE_IMAGE) {
            %>
                <img src="<%=blog.getRealMediaUrl()%>">
                <br/><br/>
            <%
        } else if (blogType == Constants.BLOG_TYPE_VIDEO) {
            %>
                <video src="<%=blog.getRealMediaUrl()%>" controls="controls">
                    Not Supported
                </video><br/><br/>
            <%
        }
    %>

    赞同数:<%=BlogDAO.getLikedTimes(blogId)%><br/><br/>

    <%
        if (curUser.getId() == blog.getOwnerId()) {
            String postfix = Communication.BLOG_ID + "=" + blogId;
            String update = cp + PageUrls.UPDATE_BLOG + "?" + postfix;
            String delete = cp + PageUrls.DELETE_BLOG_PROCESS + "?" + postfix;
            %>
                <a href="<%=update%>">编辑</a>
                <a href="<%=delete%>">删除</a>
            <%
        } else {
            BlogDAO.updateVisitTimes(blogId);
            String cancelLikeOrLike = curUser.hasLikedBlog(blogId)
                    ? "取消赞同" : "赞同";
            %>
                <a href="<%=cp + UrlUtil.getLikeBlogUrl(blogId)%>">
                    <%=cancelLikeOrLike%>
                </a>
            <%
        }
    %>

    <br/><br/>

    <%
        String createComment = cp + UrlUtil.getUpdateCommentUrl(-1, blogId);
    %>
    <a href="<%=createComment%>">新的评论</a><br/>

    <%
        ArrayList<Comment> comments = CommentDAO.getComments(blogId);
        if (comments.isEmpty()) {
            out.println("<br/>暂无评论");
        } else {
            %>
            <ul style="list-style-type: none">
            <%
                long userId = curUser.getId();
                for (Comment comment : comments) {
                %>
                    <li>
                    <%=StringUtil.unescape(comment.getContent())%><br/>
                    <%="赞同数:" + CommentDAO.getLikedTimes(comment.getId())%>
                    <br/>

                    <%
                    long commentId = comment.getId();
                    if (comment.getOwnerId() == userId) {
                        %>
                        <a href="<%=cp + UrlUtil.getUpdateCommentUrl(
                            commentId, blogId)%>"
                        >编辑</a>
                        <a href="<%=cp + UrlUtil.getDeleteCommentProcessUrl(commentId)%>"
                        >删除</a>
                        <%
                    } else {
                        String url = cp + PageUrls.LIKE_COMMENT_PROCESS + "?"
                                + Communication.COMMENT_ID + "=" + commentId;
                        boolean liked = curUser.hasLikedComment(commentId);
                        String cancelLikeOrLike = liked ? "取消赞同" : "赞同";
                        %>
                        <a href="<%=url%>"><%=cancelLikeOrLike%></a>
                        <%
                    }
                    %>
                    <br/><br/>
                    </li>
                    <%
                }
            %>
            </ul>
            <%
        }
    %>

<jsp:include page="<%=cp + UrlUtil.getFriendlyLinkUrl(blog.getOwnerId())%>"/>
</body>
</html>
