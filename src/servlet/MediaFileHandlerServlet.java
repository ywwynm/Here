package servlet;

import bean.Blog;
import bean.User;
import database.BlogDAO;
import meta.Definitions;
import util.FileUtil;
import util.LoggedInUtil;
import util.ParamUtil;
import util.StringUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 张启 on 2016/4/17.
 * media file handler
 */
@MultipartConfig
public class MediaFileHandlerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User curUser = LoggedInUtil.getLoggedInUser(request);
        // ignore the case if curUser is null because there is a global error page.
        Long blogId = ParamUtil.getBlogId(request);
        Blog blog = null;
        Short blogType;
        if (blogId == -1) {
            blogType = ParamUtil.getBlogType(request);
        } else {
            blog = BlogDAO.getBlogById(blogId);
            if (blog == null || curUser.getId() != blog.getOwnerId()) {
                return;
            }
            blogType = blog.getType();
        }

        if (Blog.isMediaType(blogType)) {
            Part mediaFilePart = ParamUtil.getMediaFile(request);
            String mediaUrl;
            if (mediaFilePart == null || mediaFilePart.getSize() == 0) {
                if (blogId == -1) {
                    response.setCharacterEncoding("GBK");
                    PrintWriter out = response.getWriter();
                    out.println("请上传媒体文件");
                    return;
                } else {
                    mediaUrl = StringUtil.unescape(blog.getMediaUrl());
                }
            } else {
                mediaUrl = FileUtil.saveFile(request, mediaFilePart,
                        curUser.getId(), blogType);
            }
            request.setAttribute(Definitions.Communication.MEDIA_URL, mediaUrl);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(
                Definitions.PageUrls.UPDATE_BLOG_PROCESS);
        dispatcher.forward(request, response);
    }
}
