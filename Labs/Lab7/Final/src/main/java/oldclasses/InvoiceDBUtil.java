package oldclasses;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDBUtil {

    private static InvoiceDBUtil instance;
    private DataSource dataSource;
    private String jndiName = "java:comp/env/jdbc/Invoices";

    private InvoiceDBUtil() throws Exception {
        dataSource = getDataSource();
    }

    public static InvoiceDBUtil getInstance() throws Exception {
        if (instance == null) {
            instance = new InvoiceDBUtil();
        }

        return instance;
    }

    private DataSource getDataSource() throws NamingException {
        Context context = new InitialContext();

        DataSource theDataSource = (DataSource) context.lookup(jndiName);

        return theDataSource;
    }

    public List<Invoice> getInvoices() throws Exception {

        List<Invoice> invoices = new ArrayList<>();

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from Invoices order by Invoice_id";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);


            while (myRs.next()) {

                int id = myRs.getInt("Invoice_id");
                String cashier = myRs.getString("Cashier");
                String items = myRs.getString("items");
                String orderDate = myRs.getString("Order_date");
                String dueDate = myRs.getString("Due_date");

                Invoice temp = new Invoice(id, cashier, orderDate, dueDate, items.split(","));


                invoices.add(temp);
            }

            return invoices;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void addInvoice(Invoice invoice) throws Exception {
        ResultSet myRs = null;
        Connection myConn = null;
        PreparedStatement myStmt = null;
        int lastID=0;

        try {
            myConn = getConnection();

            String sql = "INSERT INTO Invoices (Cashier,items,Order_date,Due_date) values (?,?,?,?)";
            String itemsStr = "";
            String[] itemsArr = invoice.getItems();
            for (int i = 0; i < itemsArr.length; i++) {
                itemsStr = itemsStr + itemsArr[i];
                if (itemsArr.length > 1 && i != itemsArr.length - 1) {
                    itemsStr = itemsStr + ",";
                }
            }

            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setString(1, invoice.getName());
            myStmt.setString(2, itemsStr);
            myStmt.setString(3, invoice.getOrderDate());
            myStmt.setString(4, invoice.getDueDate());
            myStmt.execute();

            sql = "SELECT Last_INSERT_ID(Invoice_id) as lastid from Invoices;";
            Statement myStmt2 = null;
            myStmt2 = myConn.createStatement();
            myRs = myStmt2.executeQuery(sql);
            while (myRs.next()) {
                lastID = myRs.getInt("lastid");
            }


            for (int i = 0; i < itemsArr.length; i++) {
                sql = "INSERT INTO Invoice_item (Invoice_id,Item_id) values (?,?);";
                myStmt = myConn.prepareStatement(sql);
                myStmt.setInt(1, lastID);
                myStmt.setInt(2,Integer.parseInt(itemsArr[i]));
                myStmt.execute();


            }
        } finally {
            close(myConn, myStmt);


        }

    }

    public Invoice getInvoice(int invoiceId) throws Exception {

        // Establish connection, statement and result set objects
        // Make a parameterized select statement
        // Populate a single Actor with the resultset
        // Handle any potential exceptions
        // Close all objects on finally
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = getConnection();

            String sql = "select * from Invoices where (Invoice_id = ?)";

            myStmt = myConn.createStatement();

            myRs = myStmt.executeQuery(sql);


            // retrieve data from result set row
            int id = myRs.getInt("Invoice_id");
            String cashier = myRs.getString("Cashier");
            String items = myRs.getString("items");
            String orderDate = myRs.getString("Order_date");
            String dueDate = myRs.getString("Due_date");
            // create new actor object
            Invoice temp = new Invoice(id, cashier, orderDate, dueDate, items.split(","));


            return temp;
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void updateInvoice(Invoice invoice) throws Exception {

        // Establish connection, statement objects

        // Make a parameterized update statement

        // Execute the statement

        // Handle any potential exceptions

        // Close all objects on finally

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();
            String itemsStr = "";
            String[] itemsArr = invoice.getItems();
            for (int i = 0; i < itemsArr.length; i++) {
                itemsStr = itemsStr + itemsArr[i];
                if (itemsArr.length > 1 && i != itemsArr.length - 1) {
                    itemsStr = itemsStr + ",";
                }
            }

            String sql = "UPDATE Invoices SET Cashier =?, items= ?,Order_date=?,Due_date=? WHERE (Invoice_id = ?);";

            myStmt = myConn.prepareStatement(sql);
            // set params
            myStmt.setString(1, invoice.getName());
            myStmt.setString(2, itemsStr);
            myStmt.setString(3, invoice.getOrderDate());
            myStmt.setString(4, invoice.getDueDate());
            myStmt.setInt(5, invoice.getId());
            myStmt.execute();

            sql = "DELETE FROM Invoice_item WHERE Invoice_id = ?;";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, invoice.getId());
            myStmt.execute();

            for (int i = 0; i < itemsArr.length; i++) {
                sql = "INSERT INTO Invoice_item (Invoice_id,Item_id) values (?,?)";
                myStmt = myConn.prepareStatement(sql);
                myStmt.setInt(1, invoice.getId());
                myStmt.setInt(2, Integer.parseInt(itemsArr[i]));
                myStmt.execute();

            }

        } finally {
            close(myConn, myStmt);

        }


    }

    public void deleteInvoice(int invoiceId) throws Exception {

        // Establish connection, statement objects

        // Make a parameterized delete statement

        // Execute the statement

        // Handle any potential exceptions

        // Close all objects on finally

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            myConn = getConnection();


            String sql = "DELETE FROM Invoice_item WHERE Invoice_id = ?;";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, invoiceId);
            myStmt.execute();

            sql = "DELETE FROM Invoices WHERE Invoice_id = ?;";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, invoiceId);
            myStmt.execute();

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
