package com.myapp.test;

// email.java
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/Hello")
public class Email extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String htmlContent = "<html><body><h1>Hello, Tomcat!</h1><h1>This is a second header</h1></body></html>";
        response.getWriter().println(htmlContent);
    }
}