<%@ page import="util.ParamUtil" %>
<%@ page import="bean.User" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="bean.Blog" %>
<%@ page import="database.BlogDAO" %>
<%@ page import="util.LoggedInUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/17
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>赞同中</title>
</head>
<body>
<%
    Long blogId = ParamUtil.getBlogId(request);
    User curUser = LoggedInUtil.getLoggedInUser(request);
    long userId = curUser.getId();
    if (blogId == null) {
        response.sendRedirect(cp + UrlUtil.getIndexUrl(userId));
    } else {
        Blog blog = BlogDAO.getBlogById(blogId);
        if (blog == null) {
            response.sendRedirect(cp + UrlUtil.getIndexUrl(userId));
        } else if (blog.getOwnerId() == userId) {
            response.sendRedirect(cp + UrlUtil.getBlogDetailUrl(blogId));
        } else {
            if (curUser.hasLikedBlog(blogId)) {
                curUser.cancelLikeBlog(blogId);
            } else {
                curUser.likeBlog(blogId);
            }
            response.sendRedirect(cp + UrlUtil.getBlogDetailUrl(blogId));
        }
    }
%>
</body>
</html>
