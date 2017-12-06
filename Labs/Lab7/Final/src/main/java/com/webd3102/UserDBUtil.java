package com.webd3102;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDBUtil {

    private static UserDBUtil instance;
    private DataSource dataSource;
    private String jndiName = "java:comp/env/jdbc/final";

    UserDBUtil() throws Exception {
        dataSource = getDataSource();
    }

    public static UserDBUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new UserDBUtil();
        }

        return instance;
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();

        DataSource theDataSource = (DataSource) context.lookup(jndiName);

        return theDataSource;
    }


    public List<User> getUsers() throws Exception {

        List<User> users = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from users order by id";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);


            while (myRs.next()) {

                int id = myRs.getInt("id");
                String first_name = myRs.getString("first_name");
                String last_name = myRs.getString("last_name");
                String user_name = myRs.getString("user_name");
                Double balance = myRs.getDouble("balance");
                String email = myRs.getString("email");
                String password = myRs.getString("password");


                User temp = new User(id, first_name, last_name, user_name, balance, email, password);


                users.add(temp);
            }
            return users;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void updateUser(User user) {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();

            String sql = "UPDATE users SET balance=? WHERE (id = ?);";

            myStmt = myConn.prepareStatement(sql);
            // set params
            myStmt.setDouble(1, user.getBalance());
            myStmt.setInt(2, user.getId());
            myStmt.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt);

        }

    }

    public User getUser(String user_name) {

        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        User user = new User();
        try {
            myConn = getConnection();

            String sql = "select * from users where (user_name = ?)";

            myStmt = myConn.prepareStatement(sql);
            myStmt.setString(1, user_name);
            myRs = myStmt.executeQuery();


            // retrieve data from result set row
            while (myRs.next()) {
                int id = myRs.getInt("id");
                String first_name = myRs.getString("first_name");
                String last_name = myRs.getString("last_name");
                Double balance = myRs.getDouble("balance");
                String email = myRs.getString("email");
                String password = myRs.getString("password");

                // create new actor object
                user = new User(id, first_name, last_name, user_name, balance, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }
        return user;
    }

    public boolean checkAdmin(User user) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;
        try {
            myConn = getConnection();

            String sql = "select * from role_user where (role_id = 1 AND user_id = ?)";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, user.getId());
            myRs = myStmt.executeQuery();


            // retrieve data from result set row
            while (myRs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }

        return false;
    }


    public void addUser(User user, String isAdmin) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "INSERT INTO users (first_name,last_name,user_name,email,password,balance) values (?,?,?,?,?,?)";

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, user.getFirst_name());
            myStmt.setString(2, user.getLast_name());
            myStmt.setString(3, user.getUser_name());
            myStmt.setString(4, user.getEmail());
            myStmt.setString(5, user.getPassword());
            myStmt.setDouble(6, 1000);
            myStmt.execute();
            if (isAdmin.equals("on")) {
                int id = getUser(user.getUser_name()).getId();
                sql = "INSERT INTO role_user (role_id,user_id) values (1,?)";
                myStmt = myConn.prepareStatement(sql);
                myStmt.setInt(1, id);
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
