package com.myapp.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet("/search")
public class SearchUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchKeyword = request.getParameter("searchKeyword");

        try {
            DatabaseUtils databaseUtils = new DatabaseUtils();
            List<User> searchResults = databaseUtils.searchUsers(searchKeyword);

            // Convert searchResults to JSON format
            Gson gson = new Gson();
            String jsonSearchResults = gson.toJson(searchResults);

            // troubleshooting...
            System.out.println(jsonSearchResults);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonSearchResults);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchKeyword = request.getParameter("searchKeyword");

        try {
            DatabaseUtils databaseUtils = new DatabaseUtils();
            List<User> searchResults = databaseUtils.searchUsers(searchKeyword);
            
            // Convert search results to JSON and send as response
            Gson gson = new Gson();
            String jsonResults = gson.toJson(searchResults);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResults);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database Error");
        }
    }

}