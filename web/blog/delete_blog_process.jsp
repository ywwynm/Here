<%@ page import="bean.User" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="bean.Blog" %>
<%@ page import="database.BlogDAO" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2015/4/16
  Time: 23:09
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>删除文章中</title>
</head>
<body>
<%
    Long blogId = ParamUtil.getBlogId(request);
    if (blogId == null) {
        // TODO: 2015/12/27 wrong param.
        return;
    }

    User curUser = LoggedInUtil.getLoggedInUser(request);

    Blog blog = BlogDAO.getBlogById(blogId);
    if (blog == null || blog.getOwnerId() != curUser.getId()) {
        response.sendRedirect(cp + UrlUtil.getIndexUrl(curUser.getId()));
    } else {
        boolean deleted = BlogDAO.deleteBlog(blogId);
        if (deleted) {
            response.sendRedirect(cp + UrlUtil.getIndexUrl(curUser.getId()));
        } else {
            out.println("删除失败");
        }
    }
%>
</body>
</html>
