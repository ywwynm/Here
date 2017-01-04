<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.FriendlyLink" %>
<%@ page import="database.FriendlyLinkDAO" %>
<%@ page import="util.StringUtil" %>
<%@ page import="meta.Definitions" %>
<%@ page import="util.UrlUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/18
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>管理友情链接</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- 引入 Bootstrap -->
    <link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- 包括所有已编译的插件 -->
    <script src="js/bootstrap.min.js"></script>
</head>
<body background="../images/wood_pattern.jpg">
<a href="<%=cp + Definitions.PageUrls.CREATE_FRIENDLY_LINK%>">新的友情链接</a><br/>
<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    ArrayList<FriendlyLink> friendlyLinks
            = FriendlyLinkDAO.getFriendlyLinks(curUser.getId());
    if (friendlyLinks.isEmpty()) {
        out.println("暂无友情链接");
    } else {
        %>
        <ul style="list-style-type: none">
            <%
                for (FriendlyLink link : friendlyLinks) {
                    String linkUrl = StringUtil.unescape(link.getLinkUrl());
                    %>
                    <a href="<%="http:\\\\" + linkUrl%>"><%=linkUrl%></a><br/>
                    <a href="<%=cp + UrlUtil.getDeleteFriendlyLinkProcessUrl(link.getId())%>">
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
