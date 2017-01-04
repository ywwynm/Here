<%@ page import="bean.User" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="bean.Comment" %>
<%@ page import="database.CommentDAO" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="util.LoggedInUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/17
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>删除评论中</title>
</head>
<body>
<%
    Long commentId = ParamUtil.getCommentId(request);
    User curUser = LoggedInUtil.getLoggedInUser(request);

    Comment comment = CommentDAO.getCommentById(commentId);
    if (comment == null || comment.getOwnerId() != curUser.getId()) {
        response.sendRedirect(
                cp + UrlUtil.getIndexUrl(curUser.getId()));
    } else {
        boolean deleted = CommentDAO.deleteComment(commentId);
        if (deleted) {
            response.sendRedirect(
                    cp + UrlUtil.getBlogDetailUrl(comment.getBlogId()));
        } else {
            out.println("删除失败");
        }
    }
%>
</body>
</html>
