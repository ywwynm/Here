<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="meta.Definitions" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/17
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %><html>
<head>
    <title>登出</title>
</head>
<body>
<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    if (curUser != null) {
        LoggedInUtil.deleteUserCookie(curUser.getId(), session, response);
    }
    response.sendRedirect(request.getContextPath() + Definitions.PageUrls.LOGIN);
%>
</body>
</html>
