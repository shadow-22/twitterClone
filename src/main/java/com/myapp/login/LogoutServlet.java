package com.myapp.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the current session, or create one if it doesn't exist
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Invalidate the session to log the user out
            session.invalidate();
        }

        // Redirect the user to the login page after logout
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // Perform logout logic here, e.g., invalidate the session
        HttpSession session = request.getSession();
        session.invalidate();

        // Redirect the user to a logout success page or login page
        response.sendRedirect("index.jsp");
    }
}