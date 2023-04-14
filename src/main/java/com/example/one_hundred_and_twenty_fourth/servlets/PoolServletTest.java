package com.example.one_hundred_and_twenty_fourth.servlets;


import com.example.one_hundred_and_twenty_fourth.db.ConnectionPool;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(name = "poolServletTest", value = "/dbservlet")
public class PoolServletTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

/* ! */        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.setUserName("root");
        connectionPool.setPassword("sadnasKFABFKAWFBAjk6135KBAFKAJB FAKLWJFN11354fafsefdxf3dx51351531fdxfdx1fdxf351531KUBJVJVBADKWDBAKDB");
        connectionPool.setDatabaseUrl("jdbc:mysql://localhost:3306/bd2");












        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = connectionPool.getConnection();
            String query = "select * from user";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet != null && resultSet.next()) {
                out.println("<html><body>");
                out.println("<p>" + resultSet.getString("username") + " ");
                out.println(resultSet.getString("email") + " ");
                out.println(resultSet.getString(2) + "</p>");
                out.println("</body></html>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
