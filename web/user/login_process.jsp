<%@ page import="meta.Definitions" %>
<%@ page import="database.UserDAO" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="util.LoggedInUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/14
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" class="bean.User"/>
<jsp:setProperty name="user" property="*"/>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>登录中</title>
</head>
<body>
<%
    user = UserDAO.login(user.getEmail(), user.getPassword());
    if (user != null) {
        session.setAttribute(Definitions.Communication.USER, user);

        boolean rememberMe = ParamUtil.getRememberMe(request);
        if (rememberMe) {
            LoggedInUtil.addUserCookie(user.getId(), response);
        }

        response.sendRedirect(cp + UrlUtil.getIndexUrl(user.getId()));
    } else {
        %>
        登录失败，请检查您的Email和密码是否正确<br/>
        <a href="<%=cp + Definitions.PageUrls.LOGIN%>">返回登录界面</a>
        <%
    }
%>
</body>
</html>
