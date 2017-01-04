<%@ page import="database.UserDAO" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.Message" %>
<%@ page import="database.MessageDAO" %>
<%@ page import="meta.Definitions" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="util.StringUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/18
  Time: 19:46
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>这儿</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <!-- 引入 Bootstrap -->
    <link href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- 包括所有已编译的插件 -->
    <script src="js/bootstrap.min.js"></script>
</head>
<body background="../images/wood_pattern.jpg">
<%
    Long urlUserId = ParamUtil.getUserId(request);
    Short blogType = ParamUtil.getBlogType(request);
    if (urlUserId == null || UserDAO.getUserById(urlUserId) == null
            || blogType == null || blogType != Definitions.Constants.BLOG_TYPE_MESSAGE) {
        // TODO: 2015/12/27 wrong param.
        return;
    }

    int pageNo = ParamUtil.getPageNo(request);
    int pageSize = Definitions.Constants.PAGE_SIZE_MESSAGE;

    ArrayList<Message> messages;
    User curUser = LoggedInUtil.getLoggedInUser(request);
    long curId = curUser.getId();
    if (curId == urlUserId) {
        messages = MessageDAO.getMessagesByToUserId(
                urlUserId, pageNo * pageSize, pageSize);
    } else {
        %>
        <a href="<%=cp + UrlUtil.getUpdateMessageUrl(-1, urlUserId)%>"
        >给他/她/它留言</a><br/>
        <%
        messages = MessageDAO.getMessages(curId, urlUserId);
    }

    if (messages.isEmpty()) {
        out.println("暂无留言");
    } else {
        %>
        <ul class="nav nav-pills">
            <%
                if (curId == urlUserId) {
                    for (Message message : messages) {
                        User from = UserDAO.getUserById(message.getFromUserId());
                        out.println(from.getUsername() + ":"
                                + StringUtil.unescape(message.getContent()) + "<br/><br/>");
                    }
                } else {
                    for (Message message : messages) {
                        User from = UserDAO.getUserById(message.getFromUserId());
                        out.println(from.getUsername() + ":"
                                + StringUtil.unescape(message.getContent()) + "<br/>");
                        %>
                            <a href="<%=cp + UrlUtil.getUpdateMessageUrl(
                                message.getId(), urlUserId)%>">编辑</a>
                            <a href="<%=cp + UrlUtil.getDeleteMessageProcessUrl(
                                message.getId(), urlUserId)%>">删除</a><br/><br/>
                        <%
                    }
                }
            %>
        </ul>
        <ul class="nav nav-pills">
        <%
    }

    if (curId == urlUserId && !messages.isEmpty()) {
        int totalPageCount = MessageDAO.getPageCount(urlUserId);
        String firstPage = cp + UrlUtil.getIndexUrl(
                curUser.getId(), blogType, 0);
        String prevPage  = cp + UrlUtil.getIndexUrl(
                curUser.getId(), blogType, pageNo - 1);
        String nextPage  = cp + UrlUtil.getIndexUrl(
                curUser.getId(), blogType, pageNo + 1);

        if (totalPageCount != 1) {
            if (pageNo != 0) {
                %><li class="active"><a href="<%=firstPage%>">首页</a></li><%
            }
            if (pageNo > 0) {
                %><li><a href="<%=prevPage%>">上一页</a></li><%
            }
            if (pageNo + 1 != totalPageCount) {
                %><li><a href="<%=nextPage%>">下一页</a></li><%
            }
        }
        %></ul><%
    }
%>

<jsp:include page="<%=cp + UrlUtil.getFriendlyLinkUrl(urlUserId)%>"/>
</body>
</html>
