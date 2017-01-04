<%@ page import="bean.User" %>
<%@ page import="database.BlogDAO" %>
<%@ page import="bean.Blog" %>
<%@ page import="util.*" %>
<%@ page import="meta.Definitions" %>

<%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/15
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="updatedBlog" class="bean.Blog" />
<jsp:setProperty name="updatedBlog" property="*"/>

<%
    String cp = request.getContextPath();
%>

<html>
<%
    User curUser = LoggedInUtil.getLoggedInUser(request);
    Long blogId = ParamUtil.getBlogId(request);
    Blog originalBlog = null;
    Short blogType;
    if (blogId == -1) {
        blogType = ParamUtil.getBlogType(request);
    } else {
        originalBlog = BlogDAO.getBlogById(blogId);
        if (originalBlog == null) {
            response.sendRedirect(cp + UrlUtil.getIndexUrl(blogId));
            return;
        } else if (originalBlog.getOwnerId() != curUser.getId()) {
            response.sendRedirect(cp + UrlUtil.getBlogDetailUrl(blogId));
        }
        blogType = originalBlog.getType();
    }
%>
<head>
    <title>更新<%=Blog.getTypeDescription(blogType)%>中</title>
</head>
<body>

<%
    updatedBlog.setType(blogType);

    String mediaUrl = ParamUtil.getMediaUrlAttr(request);
    if (Blog.isMediaType(blogType)) {
        updatedBlog.setMediaUrl(mediaUrl == null ? "" : mediaUrl);
    } else {
        updatedBlog.setMediaUrl("");
    }

    if (updatedBlog.getTitle() == null) {
        updatedBlog.setTitle("");
    }
    if (updatedBlog.getContent() == null) {
        updatedBlog.setContent("");
    }
    if (updatedBlog.getTags() == null) {
        updatedBlog.setTags("");
    }
    if (blogId == -1) {
        updatedBlog.setOwnerId(curUser.getId());
        boolean created = BlogDAO.createBlog(updatedBlog);
        if (created) {
            response.sendRedirect(cp + UrlUtil.getIndexUrl(curUser.getId(), blogType));
        } else {
            %>
            创建失败<br/>
            <a href="<%=cp + UrlUtil.getIndexUrl(curUser.getId())%>">返回首页</a>
            <%
        }
    } else {
        updatedBlog.setId(blogId);
        updatedBlog.setUpdateTime(System.currentTimeMillis());
        boolean updated = BlogDAO.updateBlogContents(updatedBlog);
        if (updated) {
            if (Blog.isMediaType(blogType)) {
                String oMediaUrl = originalBlog.getMediaUrl();
                oMediaUrl = StringUtil.unescape(oMediaUrl);
                if (!oMediaUrl.equals(mediaUrl)) {
                    FileUtil.deleteFile(request.getServletContext().getRealPath("/")
                            + Definitions.Constants.MEDIA_FILE_URL_ROOT + oMediaUrl);
                }
            }
            response.sendRedirect(cp + UrlUtil.getBlogDetailUrl(blogId));
        } else {
            %>
            更新失败<br/>
            <a href="<%=cp + UrlUtil.getIndexUrl(curUser.getId())%>">返回首页</a>
            <%
        }
    }

%>
</body>
</html>
