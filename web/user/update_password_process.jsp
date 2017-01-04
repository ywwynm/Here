<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="meta.Definitions" %>
<%@ page import="database.UserDAO" %>
<%@ page import="util.UrlUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/17
  Time: 13:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>更新密码中</title>
</head>
<body>
<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    String oPass = request.getParameter(
            Definitions.Communication.ORIGINAL_PASSWORD);
    if (!oPass.equals(curUser.getPassword())) {
        %>
        原密码错误<br/>
        <a href="<%=cp + Definitions.PageUrls.UPDATE_PASSWORD%>">返回</a>
        <%
        return;
    }

    String nPass = request.getParameter(
            Definitions.Communication.NEW_PASSWORD);
    String nPassRepeat = request.getParameter(
            Definitions.Communication.NEW_PASSWORD_REPEAT);
    if (!nPass.equals(nPassRepeat)) {
        return;
    }

    boolean updated = UserDAO.updatePassword(curUser.getId(), nPass);
    if (updated) {
        out.println("修改成功");
    } else {
        out.println("修改失败");
    }
%>
<a href="<%=cp + UrlUtil.getIndexUrl(curUser.getId())%>">返回主页</a>
</body>
</html>
