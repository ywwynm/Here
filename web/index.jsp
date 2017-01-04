<%@ page import="meta.Definitions.*" %>
<%@ page import="util.ParamUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/14
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <title>这儿</title>
</head>
<body>

<%
    Long urlUserId = ParamUtil.getUserId(request);
    Short blogType = ParamUtil.getBlogType(request);
%>

<jsp:include page="<%=cp + PageUrls.NAV_BAR%>">
    <jsp:param name="user_id" value="<%=urlUserId%>"/>
</jsp:include>

<%
    if (blogType != Constants.BLOG_TYPE_MESSAGE) {
        %>
            <jsp:include page="<%=PageUrls.BLOG_INDEX%>"/>
        <%
    } else {
        %>
            <jsp:include page="<%=PageUrls.MESSAGE_INDEX%>"/>
        <%
    }
%>

</body>
</html>
