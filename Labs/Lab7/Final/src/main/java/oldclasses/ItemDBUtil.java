package oldclasses;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class ItemDBUtil {

    private static ItemDBUtil instance;
    private DataSource dataSource;
    private String jndiName = "java:comp/env/jdbc/Invoices";

    private ItemDBUtil() throws Exception {
        dataSource = getDataSource();
    }

    public static ItemDBUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new ItemDBUtil();
        }

        return instance;
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();

        DataSource theDataSource = (DataSource) context.lookup(jndiName);

        return theDataSource;
    }

    public Map<String,String> getItems() throws Exception {

        Map<String,String> items = new LinkedHashMap<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from Items order by Item_id";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);

            // process result set
            while (myRs.next()) {


                items.put(myRs.getString("Item_id"),myRs.getString("Item_name"));
            }

            return items;
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
