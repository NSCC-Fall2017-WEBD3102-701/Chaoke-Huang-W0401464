package com.webd3102;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDBUtil {

    private static ProductDBUtil instance;
    private DataSource dataSource;
    private String jndiName = "java:comp/env/jdbc/final";

    ProductDBUtil() throws Exception {
        dataSource = getDataSource();
    }

    public static ProductDBUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new ProductDBUtil();
        }

        return instance;
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();

        DataSource theDataSource = (DataSource) context.lookup(jndiName);

        return theDataSource;
    }

    public List<Product> getProducts() throws Exception {

        List<Product> products = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from products order by id";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);


            while (myRs.next()) {

                int id = myRs.getInt("id");
                String name = myRs.getString("name");
                String description = myRs.getString("description");
                Double price = myRs.getDouble("price");
                String pic_ref =myRs.getString("pic_ref");

                Product temp = new Product(id, name, price, description,pic_ref);


                products.add(temp);
            }

            return products;
        } finally {
            close(myConn, myStmt, myRs);
        }
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
