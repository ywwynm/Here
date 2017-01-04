<%@ page import="bean.User" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="meta.Definitions" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="util.UrlUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/17
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>更改密码</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="../css/login.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="../css/sui.min.css" type="text/css" />
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/sui.min.js"></script>
</head>
<body background="../images/wood_pattern.jpg">
<script>
    function check(form) {
        var nPass = form.new_password.value;
        var nPassRepeat = form.new_password_repeat.value;
        if (nPass != nPassRepeat) {
            alert("两次输入的密码不一致!");
            return false;
        }
        return true;
    }
</script>
<section class="main">
    <form method="post" action="<%=cp + Definitions.PageUrls.UPDATE_PASSWORD_PROCESS%>"
          onsubmit="return check(this)" class="form">
        <h1 style="text-align:center">
            <span class="sign-up">修改密码</span>
        </h1>
        <p class="float">
                <i class="icon-user">
                    <img src="../images/lock.png" />
                </i>
                "原密码"
            <input title="originalPassword" name="original_password" type="password" placeholder="原密码" />
        </p>
        <p class="float">
                <i class="icon-user">
                    <img src="../images/lock.png" />
                </i>
                "新密码"
            <input title="newPassword" name="new_password" type="password" placeholder="新密码" />
        </p>
        <p class="float">
                <i class="icon-lock">
                    <img src="../images/lock.png" />
                </i>
                "重复密码"
            <input type="password"  title="newPasswordRepeat" name="new_password_repeat" placeholder="重复密码" class="showpassword" />
        </p>
        <p  class="float">
            <button class="sui-btn btn-primary" name="submit" type="submit">确认</button>
        </p>
    </form>
</section>
</body>
</html>
