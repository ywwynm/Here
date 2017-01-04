<%@ page import="bean.User" %>
<%@ page import="meta.Definitions" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="static meta.Definitions.Constants.*" %>
<%@ page import="database.UserDAO" %>
<%@ page import="util.LoggedInUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/17
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title></title>
</head>
<body>
<%
    Long urlUserId = ParamUtil.getUserId(request);
    if (urlUserId == null) {
        return;
    }
    User urlUser = UserDAO.getUserById(urlUserId);
    if (urlUser == null) {
        return;
    }
%>

<a href="<%=cp + UrlUtil.getIndexUrl(urlUserId, BLOG_TYPE_ARTICLE)%>">文章</a>
<a href="<%=cp + UrlUtil.getIndexUrl(urlUserId, BLOG_TYPE_IMAGE)%>">图片</a>
<a href="<%=cp + UrlUtil.getIndexUrl(urlUserId, BLOG_TYPE_VIDEO)%>">视频</a>
<a href="<%=cp + UrlUtil.getIndexUrl(urlUserId, BLOG_TYPE_MESSAGE)%>">留言</a>

<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    if (curUser == null) {
        %>
            <a href="<%=cp + Definitions.PageUrls.REGISTER%>">注册</a>
            <a href="<%=cp + Definitions.PageUrls.LOGIN%>">登录</a>
        <%
    } else {
        long curId = curUser.getId();
        if (curId == urlUserId) {
            %>
            <a href="<%=cp + Definitions.PageUrls.SETTING_INDEX%>">
                <%=urlUser.getUsername()%></a>
            <a href="<%=cp + Definitions.PageUrls.LOGOUT_PROCESS%>">注销</a>
            <%
        } else {
            %>
            <a href="<%=cp + UrlUtil.getIndexUrl(curId)%>">返回我的主页</a>
            <%
            String addOrDelete = curUser.hasFriend(urlUserId) ? "删除好友" : "加为好友";
            %>
            <a href="<%=cp + UrlUtil.getUpdateFriendshipProcessUrl(urlUserId)%>">
                <%=addOrDelete%></a>
            <%
        }
    }
%>
<br/><br/>

</body>
</html>
