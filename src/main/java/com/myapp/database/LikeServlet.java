package com.myapp.database;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int postId = Integer.parseInt(request.getParameter("postId"));
        boolean isLiked = Boolean.parseBoolean(request.getParameter("isLiked"));

        if (username != null) {
            if (isLiked) {
                DatabaseUtils.addLike(postId, username);
            } else {
                DatabaseUtils.removeLike(postId, username);
            }
            int likeCount = DatabaseUtils.getLikeCount(postId);
            response.setContentType("application/json");
            response.getWriter().write("{\"likeCount\": " + likeCount + "}");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }


    }

}
