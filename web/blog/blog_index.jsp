<%@ page import="util.ParamUtil" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="database.BlogDAO" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="bean.User" %>
<%@ page import="bean.Blog" %>
<%@ page import="meta.Definitions" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="util.StringUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/14
  Time: 18:11
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

    User curUser = LoggedInUtil.getLoggedInUser(request);
    if (curUser.getId() == urlUserId) {
        %>
            <ul class="nav nav-pills">
                <li class="active">
                    <a href="<%=cp + UrlUtil.getCreateBlogUrl(blogType)%>">创建</a>
                </li>
            </ul>
        <%
    }

    int pageNo = ParamUtil.getPageNo(request);
    int totalPageCount = BlogDAO.getPageCount(urlUserId, blogType);
    if (pageNo >= totalPageCount && totalPageCount != 0) {
        response.sendRedirect(cp + UrlUtil.getIndexUrl(urlUserId, blogType));
        return;
    }

    int pageSize = Definitions.Constants.PAGE_SIZE_BLOG;
    ArrayList<Blog> blogs = BlogDAO.getBlogsByOwnerId(
            urlUserId, blogType, pageNo * pageSize, pageSize);
    if (blogs.isEmpty()) {
        %>空<br/><%
    } else {
        String detailUrl = cp + Definitions.PageUrls.BLOG_DETAIL + "?"
                + Definitions.Communication.BLOG_ID + "=";
        %>
        <ul class="nav nav-pills nav-stacked">
            <%
                for (Blog blog : blogs)  {
                    %>
                    <li>
                        <a href="<%=detailUrl + blog.getId()%>">
                            <%=StringUtil.unescape(blog.getTitle())%>
                        </a><br/>
                    </li>
                    <%
                }
            %>
        </ul>
        <ul class="nav nav-pills">
        <%

        String firstPage = cp + UrlUtil.getIndexUrl(curUser.getId(), blogType, 0);
        String prevPage  = cp + UrlUtil.getIndexUrl(curUser.getId(), blogType, pageNo - 1);
        String nextPage  = cp + UrlUtil.getIndexUrl(curUser.getId(), blogType, pageNo + 1);

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
