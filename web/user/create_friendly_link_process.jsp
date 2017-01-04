<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="database.FriendlyLinkDAO" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="meta.Definitions" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/18
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="friendlyLink" class="bean.FriendlyLink"/>
<jsp:setProperty name="friendlyLink" property="linkUrl"/>
<%
    String cp = request.getContextPath();
%>
<html>
<head>
    <title>创建友情链接中</title>
</head>
<body>
<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    friendlyLink.setOwnerId(curUser.getId());
    boolean created = FriendlyLinkDAO.createFriendlyLink(friendlyLink);
    if (created) {
        out.println("创建成功");
    } else {
        out.println("创建失败");
    }
%>
<br/>
<a href="<%=cp + Definitions.PageUrls.FRIENDLY_LINKS_MANAGEMENT%>">返回友情链接管理</a>
</body>
</html>
