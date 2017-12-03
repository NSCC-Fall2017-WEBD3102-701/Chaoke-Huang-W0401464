package com.webd3102;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDBUtil {

    private static PurchaseDBUtil instance;
    private DataSource dataSource;
    private String jndiName = "java:comp/env/jdbc/final";

    PurchaseDBUtil() throws Exception {
        dataSource = getDataSource();
    }

    public static PurchaseDBUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new PurchaseDBUtil();
        }
        return instance;
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();
        DataSource theDataSource = (DataSource) context.lookup(jndiName);
        return theDataSource;
    }

    public List<Purchase> getPurchasesByOrderId(int orderId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        List<Purchase> purchases = new ArrayList<>();

        try {

            myConn = getConnection();

            String sql = "select * from order_product where (order_id = ?)";

            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, orderId);
            myRs = myStmt.executeQuery();

            // retrieve data from result set row
            while (myRs.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(myRs.getInt("id"));
                purchase.setProduct(ProductDBUtil.getInstance().getProductById(myRs.getInt("product_id")));
                //purchase.setOrder(OrderDBUtil.getInstance());
                purchase.setPrice(myRs.getDouble("price"));
                purchase.setAmount(myRs.getInt("price"));
                purchase.setSubtotal(myRs.getDouble("subtotal"));

                purchases.add(purchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }
        return purchases;
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