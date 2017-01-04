<%@ page import="util.ParamUtil" %>
<%@ page import="database.FriendlyLinkDAO" %>
<%@ page import="meta.Definitions" %><%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/18
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String cp = request.getContextPath();
%>
<html>
<head>
    <title>删除友情链接中</title>
</head>
<body>
<%
    Long flId = ParamUtil.getFriendlyLinkId(request);
    boolean deleted = FriendlyLinkDAO.deleteFriendlyLink(flId);
    if (deleted) {
        out.println("删除成功");
    } else {
        out.println("删除失败");
    }
%>
<br/>
<a href="<%=cp + Definitions.PageUrls.FRIENDLY_LINKS_MANAGEMENT%>">返回友情链接管理</a>
</body>
</html>
