<%@ page import="meta.Definitions" %>
<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="util.UrlUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/14
  Time: 12:31
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>这儿</title>
    <link href="../css/login.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="../css/sui.min.css" type="text/css"/>
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/sui.min.js"></script>
</head>

<body background="../images/wood_pattern.jpg">

<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    if (curUser != null) {
        response.sendRedirect(cp + UrlUtil.getIndexUrl(curUser.getId()));
        return;
    }
%>

<section class="main">
    <form method="post" action="<%=cp + Definitions.PageUrls.LOGIN_PROCESS%>" class="form">
        <h1 style="text-align:center">
            登录
        </h1>
        <p class="float">
            <i class="icon-user">
                <img src="../images/email.png"/>
            </i>
            Email
            <input type="text" name="email" placeholder="Your Email"/>
        </p>
        <p class="float">
            <i class="icon-lock">
                <img src="../images/lock.png"/>
            </i>
            密 码
            <input type="password" name="password" placeholder="密码" class="showpassword"/>
        </p>
        <p class="float">
            <input title="rememberMe" type="checkbox" name="remember_me">记住我
        </p>
        <p class="float">
            <button class="sui-btn btn-primary" name="submit" type="submit">登录</button>
            <button class="sui-btn btn-primary" type="button"
                    onclick="javascript:window.location.href='<%=cp + Definitions.PageUrls.REGISTER%>'"
                    name="sign up">
                注册
            </button>
        </p>
    </form>
</section>
</body>
</html>
