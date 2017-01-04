<%@ page import="util.ParamUtil" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="bean.User" %>
<%@ page import="bean.Message" %>
<%@ page import="database.MessageDAO" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="meta.Definitions" %>
<%@ page import="util.StringUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/18
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <%
        Long messageId = ParamUtil.getMessageId(request);
        String pageTitle = messageId == -1 ? "新的留言" : "编辑留言";
    %>
    <title><%=pageTitle%></title>
</head>
<body>
<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    Message message = null;
    Long toUserId;
    if (messageId == -1) {
        toUserId = ParamUtil.getToUserId(request);
    } else {
        message = MessageDAO.getMessageById(messageId);
        if (message == null || message.getFromUserId() != curUser.getId()) {
            response.sendRedirect(UrlUtil.getIndexUrl(curUser.getId()));
            return;
        }
        toUserId = message.getToUserId();
    }

    String presetContent = "";
    if (messageId != -1) {
        presetContent = StringUtil.unescape(message.getContent());
    }

    String processUrl = cp + UrlUtil.getUpdateMessageProcessUrl(
            messageId, toUserId);
%>

<jsp:include page="<%=cp + Definitions.PageUrls.NAV_BAR%>">
    <jsp:param name="user_id" value="<%=curUser.getId()%>"/>
</jsp:include>

<script>
    function check(form) {
        var content = form.content.value;
        if (content.length == 0) {
            alert("留言内容无法为空");
            return false;
        }
        return true;
    }
</script>

<form action="<%=processUrl%>" method="post" onsubmit="return check(this)">
    内容<br/>
    <textarea title="content" name="content" cols="80" rows="30"
        ><%=presetContent%></textarea>
    <br/>
    <input type="submit" value="提交">
</form>

</body>
</html>
