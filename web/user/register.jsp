<%@ page import="meta.Definitions" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="bean.User" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/14
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>注册</title>
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

<script>
    function check(form) {
        var email = form.email.value;
        var username = form.username.value;
        if (email.length == 0 || username.length == 0) {
            alert("邮箱或用户名不能为空");
            return false;
        } else {
            var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
            var valid = pattern.test(email);
            if (!valid) {
                alert("请输入正确的邮箱地址");
            }
            return valid;
        }
    }
</script>

<section class="main">
    <form method="post"
          action="<%=cp + Definitions.PageUrls.REGISTER_PROCESS%>"
          class="form">
        <h1 style="text-align:center">
            <span class="sign-up">注册</span>
        </h1>
        <p class="float">
            <i class="icon-user">
                <img src="../images/email.png"/>
            </i>
            Email
            <input type="text" name="email" placeholder="Your Email"/>
        </p>
        <p class="float">
            <i class="icon-user">
                <img src="../images/people.png"/>
            </i>
            用户名
            <input type="text" name="username" placeholder="Your Name"/>
        </p>
        <p class="float">
            <i class="icon-lock">
                <img src="../images/lock.png"/>
            </i>
            密 码
            <input type="password" name="password" placeholder="密码" class="showpassword"/>
        </p>
        <p class="float">
            <button class="sui-btn btn-primary" name="submit" type="submit">注册</button>
        </p>
    </form>
</section>
</html>
