package com.webd3102;

import com.sun.org.apache.xpath.internal.operations.Or;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDBUtil {

    private static OrderDBUtil instance;
    private DataSource dataSource;
    private String jndiName = "java:comp/env/jdbc/final";

    OrderDBUtil() throws Exception {
        dataSource = getDataSource();
    }

    public static OrderDBUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new OrderDBUtil();
        }

        return instance;
    }


    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();

        DataSource theDataSource = (DataSource) context.lookup(jndiName);

        return theDataSource;
    }

    public List<Order> getOrders() throws Exception {

        List<Order> orders = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from orders order by id";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {
                Order order = new Order();
                order.setId(myRs.getInt("id"));
                order.setDate(myRs.getDate("created_at").toLocalDate());
                order.setPurchases(PurchaseDBUtil.getInstance().getPurchasesByOrderId(order.getId()));
                orders.add(order);
            }
            return orders;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addOrder(Order order) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = getConnection();

            String sql = "INSERT INTO orders (created_at, user_id, total) values (?,?,?)";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setDate(1, Date.valueOf(order.getDate()));
            myStmt.setInt(2, order.getUser().getId());
            myStmt.setDouble(3, order.getTotal());
            myStmt.execute();

            sql = "SELECT Last_INSERT_ID(id) as lastid from orders;";
            int lastID=0;
            myRs = myStmt.executeQuery(sql);
            while (myRs.next()) {
                lastID = myRs.getInt("lastid");
            }





            for (Purchase purchase : order.getPurchases()) {
                sql = "INSERT INTO order_product (order_id, product_id, amount, price, subtotal) values (?,?,?,?,?)";

                myStmt = myConn.prepareStatement(sql);

                // set params
                myStmt.setInt(1, lastID);
                myStmt.setInt(2, purchase.getProduct().getId());
                myStmt.setInt(3, purchase.getAmount());
                myStmt.setDouble(4, purchase.getPrice());
                myStmt.setDouble(5, purchase.getSubtotal());
                myStmt.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt);
        }
    }


    public List<Order> getOrdersByUser(User user){


        List<Order> orders = new ArrayList<>();

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;


        try {

            myConn = getConnection();

            String sql = "select * from orders where (user_id = ?)";

            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, user.getId());
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            while (myRs.next()) {
                Order order = new Order();
                order.setId(myRs.getInt("id"));
                order.setUser(user);
                order.setTotal(myRs.getDouble("total"));
                order.setDate(myRs.getDate("created_at").toLocalDate());
                order.setPurchases(PurchaseDBUtil.getInstance().getPurchasesByOrderId(order.getId()));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }
        return orders;



    }


    private Connection getConnection() throws Exception {

        Connection theConn = dataSource.getConnection();

        return theConn;
    }

    private void close(Connection theConn, Statement theStmt) {
        close(theConn, theStmt, null);
    }


    private void close(Connection theConn, Statement theStmt, ResultSet theRs) {

        try {
            if (theRs != null) {
                theRs.close();
            }

            if (theStmt != null) {
                theStmt.close();
            }

            if (theConn != null) {
                theConn.close();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
