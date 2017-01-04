<%@ page import="meta.Definitions" %>
<%@ page import="bean.User" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="util.LoggedInUtil" %>
<%@ page import="bean.Blog" %>
<%@ page import="database.BlogDAO" %>
<%@ page import="util.StringUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/15
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <%
        User curUser = LoggedInUtil.getLoggedInUser(request);
        Long blogId = ParamUtil.getBlogId(request);
        Blog blog = null;
        Short blogType;
        if (blogId == -1) {
            blogType = ParamUtil.getBlogType(request);
        } else {
            blog = BlogDAO.getBlogById(blogId);
            if (blog == null) {
                response.sendRedirect(cp + UrlUtil.getIndexUrl(curUser.getId()));
                return;
            } else if (blog.getOwnerId() != curUser.getId()) {
                response.sendRedirect(cp + UrlUtil.getBlogDetailUrl(blogId));
                return;
            }
            blogType = blog.getType();
        }

        String titlePostfix = Blog.getTypeDescription(blogType);
        String titlePrefix = blogId == -1 ? "新的" : "更新";
    %>
    <title><%=titlePrefix + titlePostfix%></title>
</head>
<body>

<%
    String presetTitle = "", presetContent = "", presetTags = "";
    if (blogId != -1) {
        presetTitle = StringUtil.unescape(blog.getTitle());
        presetContent = StringUtil.unescape(blog.getContent());
        presetTags = blog.getTags();
    }
%>

<jsp:include page="<%=cp + Definitions.PageUrls.NAV_BAR%>">
    <jsp:param name="user_id" value="<%=curUser.getId()%>"/>
</jsp:include>

<script>
    function check(form) {
        var title = form.title.value;
        if (title.length == 0) {
            alert("标题不能为空");
            return false;
        }
        return true;
    }
</script>

<form action="<%=cp + UrlUtil.getUpdateBlogProcessUrl(blogId, blogType)%>"
      method="post" enctype="multipart/form-data" onsubmit="return check(this)">
    标题<input title="title" name="title" value="<%=presetTitle%>">
    <br/>

    内容<br/><textarea title="content" name="content" cols="80" rows="30"
        ><%=presetContent%></textarea>
    <br/>

    标签<input title="tags" name="tags" value="<%=presetTags%>">
    <br/>

    <%
        if (Blog.isMediaType(blogType)) {
            // TODO: 2015/12/29 multi media files?
            %>
            <input title="media_file" name="media_file"
                   type="file" accept="<%=Blog.getMIMEType(blogType)%>" ><br/>
            <%
        }
    %>

    <input type="submit" value="提交">
</form>

</body>
</html>
