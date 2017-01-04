<%@ page import="meta.Definitions" %>
<%@ page import="bean.Comment" %>
<%@ page import="database.CommentDAO" %>
<%@ page import="bean.User" %>
<%@ page import="util.ParamUtil" %>
<%@ page import="util.UrlUtil" %>
<%@ page import="util.LoggedInUtil" %><%--
  Created by IntelliJ IDEA.
  User: 张启
  Date: 2016/4/16
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String cp = request.getContextPath();
%>

<html>
<head>
    <%
        Long commentId = ParamUtil.getCommentId(request);
        String pageTitle = commentId == -1 ? "新的评论" : "更新评论";
    %>
    <title><%=pageTitle%></title>
</head>
<body>
    <%
        User curUser = LoggedInUtil.getLoggedInUser(request);
        Comment comment = null;
        Long blogId;
        if (commentId == -1) {
            blogId = ParamUtil.getBlogId(request);
        } else {
            comment = CommentDAO.getCommentById(commentId);
            if (comment == null) {
                response.sendRedirect(cp + UrlUtil.getIndexUrl(curUser.getId()));
                return;
            } else {
                blogId = comment.getBlogId();
                if (comment.getOwnerId() != curUser.getId()) {
                    response.sendRedirect(cp +
                            UrlUtil.getBlogDetailUrl(blogId));
                    return;
                }
            }
        }

        String presetContent = "";
        if (commentId != -1) {
            presetContent = comment.getContent();
        }

        String processUrl = cp + UrlUtil.getUpdateCommentProcessUrl(
                commentId, blogId);
    %>

    <jsp:include page="<%=cp + Definitions.PageUrls.NAV_BAR%>">
        <jsp:param name="user_id" value="<%=curUser.getId()%>"/>
    </jsp:include>

    <script>
        function check(form) {
            var content = form.content.value;
            if (content.length == 0) {
                alert("评论内容不能为空");
                return false;
            }
            return true;
        }
    </script>

    <form action="<%=processUrl%>" method="post" onsubmit="return check(this)">
        内容<br/>
        <textarea title="content" name="content" cols="60" rows="10"
            ><%=presetContent%></textarea>
        <br/>
        <input type="submit" value="提交">
    </form>
</body>
</html>
