<%@ page import="util.ParamUtil" %>
<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="database.MessageDAO" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="meta.Definitions" %>
<%@ page import="bean.Message" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/18
  Time: 23:05
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="message" class="bean.Message"/>
<jsp:setProperty name="message" property="content"/>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>更新留言中</title>
</head>
<body>
<%
    Long messageId = ParamUtil.getMessageId(request);
    User curUser = LoggedInUtil.getLoggedInUser(request);
    if (messageId == -1) {
        Long toUserId = ParamUtil.getToUserId(request);
        message.setFromUserId(curUser.getId());
        message.setToUserId(toUserId);
        boolean created = MessageDAO.createMessage(message);
        if (created) {
            response.sendRedirect(cp + UrlUtil.getIndexUrl(
                    toUserId, Definitions.Constants.BLOG_TYPE_MESSAGE));
        } else {
            out.println("创建失败");
        }
    } else {
        Message originalMessage = MessageDAO.getMessageById(messageId);
        if (originalMessage == null
                || originalMessage.getFromUserId() != curUser.getId()) {
            response.sendRedirect(cp + UrlUtil.getIndexUrl(curUser.getId()));
            return;
        }
        message.setId(messageId);
        message.setUpdateTime(System.currentTimeMillis());
        boolean updated = MessageDAO.updateMessageContent(message);
        if (updated) {
            response.sendRedirect(cp + UrlUtil.getIndexUrl(
                    originalMessage.getToUserId(),
                    Definitions.Constants.BLOG_TYPE_MESSAGE));
        } else {
            out.println("更新失败");
        }
    }
%>
</body>
</html>
