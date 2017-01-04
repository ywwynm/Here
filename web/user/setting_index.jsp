<%@ page import="util.UrlUtil" %>
<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="meta.Definitions" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/17
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>用户中心</title>
</head>
<body>
<a href="<%=cp + Definitions.PageUrls.UPDATE_PASSWORD%>">修改密码</a><br/>
<a href="<%=cp + Definitions.PageUrls.FRIENDS_MANAGEMENT%>">管理好友</a><br/>
<a href="<%=cp + Definitions.PageUrls.FRIENDLY_LINKS_MANAGEMENT%>">管理友情链接</a><br/>
</body>
</html>
