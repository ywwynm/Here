<%@ page import="util.ParamUtil" %>
<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="bean.Message" %>
<%@ page import="database.MessageDAO" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="meta.Definitions" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/18
  Time: 23:24
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>删除留言中</title>
</head>
<body>
<%
    Long messageId = ParamUtil.getMessageId(request);
    User curUser = LoggedInUtil.getLoggedInUser(request);

    Message message = MessageDAO.getMessageById(messageId);
    if (message == null || message.getFromUserId() != curUser.getId()) {
        response.sendRedirect(
                cp + UrlUtil.getIndexUrl(curUser.getId()));
    } else {
        boolean deleted = MessageDAO.deleteMessage(messageId);
        if (deleted) {
            response.sendRedirect(cp + UrlUtil.getIndexUrl(
                    message.getToUserId(),
                    Definitions.Constants.BLOG_TYPE_MESSAGE));
        } else {
            out.println("删除失败");
        }
    }
%>
</body>
</html>
