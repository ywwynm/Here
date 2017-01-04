<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="util.StringUtil" %>
<%@ page import="bean.FriendlyLink" %>
<%@ page import="database.FriendlyLinkDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="util.ParamUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/18
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>友情链接</title>
</head>
<body>
<br/><br/>友情链接:<br/>
<%
    Long ownedId = ParamUtil.getUserId(request);
    if (ownedId == null) {
        return;
    }

    ArrayList<FriendlyLink> friendlyLinks
            = FriendlyLinkDAO.getFriendlyLinks(ownedId);
    if (friendlyLinks.isEmpty()) {
        out.println("暂无友情链接");
    } else {
        %>
        <ul style="list-style-type: none">
            <%
                for (FriendlyLink link : friendlyLinks) {
                    String linkUrl = StringUtil.unescape(link.getLinkUrl());
                    %>
                    <a href="<%="http:\\\\" + linkUrl%>"><%=linkUrl%>
                    </a><br/>
                    <%
                }
            %>
        </ul>
        <%
    }
%>
</body>
</html>
