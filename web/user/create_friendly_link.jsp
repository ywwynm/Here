<%@ page import="meta.Definitions" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/18
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>

<%
    String cp = request.getContextPath();
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>创建友情链接</title>
</head>
<body>
<form method="post" action="<%=cp + Definitions.PageUrls.CREATE_FRIENDLY_LINK_PROCESS%>">
    链接<input title="link_url" name="linkUrl">
    <input type="submit" value="提交">
</form>
</body>
</html>
