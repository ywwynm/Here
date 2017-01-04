<%@ page import="bean.User" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="database.CommentDAO" %>
<%@ page import="bean.Comment" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="database.BlogDAO" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/16
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="comment" class="bean.Comment"/>
<jsp:setProperty name="comment" property="content"/>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>更新评论中</title>
</head>
<body>
<%
    Long commentId = ParamUtil.getCommentId(request);
    User curUser = LoggedInUtil.getLoggedInUser(request);
    if (commentId == -1) {
        Long blogId = ParamUtil.getBlogId(request);
        if (blogId == null || BlogDAO.getBlogById(blogId) == null) {
            return;
        }
        comment.setBlogId(blogId);
        comment.setOwnerId(curUser.getId());
        boolean created = CommentDAO.createComment(comment);
        if (created) {
            response.sendRedirect(cp + UrlUtil.getBlogDetailUrl(blogId));
        } else {
            out.println("创建失败");
        }
    } else {
        Comment originalComment = CommentDAO.getCommentById(commentId);
        if (originalComment == null
                || originalComment.getOwnerId() != curUser.getId()) {
            response.sendRedirect(UrlUtil.getIndexUrl(curUser.getId()));
            return;
        }
        comment.setId(commentId);
        comment.setUpdateTime(System.currentTimeMillis());
        boolean updated = CommentDAO.updateCommentContent(comment);
        if (updated) {
            response.sendRedirect(cp + UrlUtil.getBlogDetailUrl(
                    originalComment.getBlogId()));
        } else {
            out.println("更新失败");
        }
    }
%>
</body>
</html>
