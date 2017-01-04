<%@ page import="meta.Definitions" %>
<%@ page import="database.UserDAO" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="bean.User" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/14
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" class="bean.User"/>
<jsp:setProperty name="user" property="*"/>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>注册中</title>
</head>
<body>
<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    if (curUser != null) {
        response.sendRedirect(cp + UrlUtil.getIndexUrl(curUser.getId()));
        return;
    }

    if (user.getPassword() == null) {
        user.setPassword("");
    }
    user = UserDAO.register(user.getEmail(), user.getUsername(), user.getPassword());
    if (user != null) {
        LoggedInUtil.addUserCookie(user.getId(), response);
        session.setAttribute(Definitions.Communication.USER, user);
        response.sendRedirect(cp + UrlUtil.getIndexUrl(user.getId()));
    } else {
        out.println("注册失败");
    }
%>
</body>
</html>
