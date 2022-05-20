package Servlet;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderid = req.getParameter("orID");
        String date = req.getParameter("odate");
        String customerid = req.getParameter("cusid");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("Insert into orders values(?,?,?)");
            pstm.setObject(1, orderid);
            pstm.setObject(2, date);
            pstm.setObject(3, customerid);


            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

}
