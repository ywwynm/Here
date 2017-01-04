<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="meta.Definitions" %>
<%@ page import="util.UrlUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/17
  Time: 9:42
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>管理好友</title>
</head>
<body>
<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    ArrayList<User> friends = curUser.getFriends();
    if (friends.isEmpty()) {
        %>
            暂无好友<br/>
            <a href="<%=cp + Definitions.PageUrls.SETTING_INDEX%>">返回</a>
        <%
    } else {
        %>
        <ul style="list-style-type: none">
            <%
                for (User friend : friends) {
                    long friendId = friend.getId();
                    %>
                        <a href="<%=cp + UrlUtil.getIndexUrl(friendId)%>">
                            <%=friend.getUsername()%>
                        </a><br/>
                        <a href="<%=cp + UrlUtil.getUpdateFriendshipProcessUrl(friendId)%>">
                            删除
                        </a><br/><br/>
                    <%
                }
            %>
        </ul>
        <%
    }
%>
</body>
</html>
