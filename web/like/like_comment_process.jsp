<%@ page import="util.ParamUtil" %>
<%@ page import="bean.User" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="bean.Comment" %>
<%@ page import="database.CommentDAO" %>
<%@ page import="bean.Blog" %>
<%@ page import="database.BlogDAO" %>
<%@ page import="util.LoggedInUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/17
  Time: 15:51
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>赞同评论中</title>
</head>
<body>
<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    long userId = curUser.getId();
    Long commentId = ParamUtil.getCommentId(request);
    if (commentId == null) {
        response.sendRedirect(cp + UrlUtil.getIndexUrl(userId));
    } else {
        Comment comment = CommentDAO.getCommentById(commentId);
        if (comment == null) {
            response.sendRedirect(cp + UrlUtil.getIndexUrl(userId));
        } else {
            Blog blog = BlogDAO.getBlogByCommentId(commentId);
            if (comment.getOwnerId() != userId) {
                if (curUser.hasLikedComment(commentId)) {
                    curUser.cancelLikeComment(commentId);
                } else {
                    curUser.likeComment(commentId);
                }
            }
            response.sendRedirect(
                    cp + UrlUtil.getBlogDetailUrl(blog.getId()));
        }
    }
%>
</body>
</html>
