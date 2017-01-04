<%@ page import="bean.User" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="database.UserDAO" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="util.LoggedInUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/16
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>更新好友中</title>
</head>
<body>
<%
    User curUser = LoggedInUtil.getLoggedInUser(request);

    Long friendId = ParamUtil.getFriendId(request);
    User friend = UserDAO.getUserById(friendId);
    if (friend == null) {
        out.println("不存在该用户");
    }

    if (curUser.hasFriend(friendId)) {
        curUser.breakWith(friendId);
        response.sendRedirect(cp + UrlUtil.getIndexUrl(curUser.getId()));
    } else {
        curUser.makeFriendWith(friendId);
        response.sendRedirect(cp + UrlUtil.getIndexUrl(friendId));
    }
%>
</body>
</html>
