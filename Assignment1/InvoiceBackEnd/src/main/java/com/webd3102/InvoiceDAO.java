package com.webd3102;

import java.util.*;

public class InvoiceDAO {
    private static final Map<String, Invoice> invoiceMap = new HashMap<String, Invoice>();
    private static String lastID;

    static {
        initInvoice();
    }

    List<Invoice> invoiceList = new ArrayList<Invoice>();

    public InvoiceDAO() {
    }

    public static Map<String, Invoice> getInvoiceMap() {
        return invoiceMap;
    }

    public static String getLastID() {
        return lastID;
    }

    public static void setLastID(String lastID) {
        InvoiceDAO.lastID = lastID;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    private static void initInvoice() {
        List<String> items1 = new ArrayList<String>();
        items1.add("OnePlus 5");
        List<String> items2 = new ArrayList<String>();
        items2.add("OnePlus 5");
        items2.add("IPhone X");
        Invoice invoice1 = new Invoice("1", "CK Huang", items1, "11/11/2017", "12/12/2017");
        Invoice invoice2 = new Invoice("2", "Derrick Ma", items2, "12/12/2017", "01/12/2018");
        invoiceMap.put(invoice1.getInvoiceNo(), invoice1);
        invoiceMap.put(invoice2.getInvoiceNo(), invoice2);
        lastID = "3";
    }

    public static List<Invoice> getAllInvoices() {
        Collection<Invoice> c = invoiceMap.values();
        List<Invoice> list = new ArrayList<Invoice>();
        list.addAll(c);
        return list;
    }

    public static Invoice getInvoice(String invoiceNo) {
        return invoiceMap.get(invoiceNo);
    }

    public static Invoice addInvoice(Invoice invoice) {
        invoiceMap.put(invoice.getInvoiceNo(), invoice);
        lastID = Integer.toString(Integer.parseInt(lastID)+1);
        return invoice;
    }

    public static Invoice updateInvoice(Invoice invoice) {
        invoiceMap.put(invoice.getInvoiceNo(), invoice);
        return invoice;
    }

    public static void deleteInvoice(String invoiceNo) {
        invoiceMap.remove(invoiceNo);
    }

    public static class Invoice {

        String invoiceNo;
        String cashier;
        List<String> items;
        String orderDate;
        String dueDate;

        public Invoice() {
        }

        public Invoice(String invoiceNo, String cashier, List<String> items, String orderDate, String dueDate) {
            this.cashier = cashier;
            this.invoiceNo = invoiceNo;
            this.items = items;
            this.orderDate = orderDate;
            this.dueDate = dueDate;
        }

        //getter and setter methods

        public String getInvoiceNo() {
            return invoiceNo;
        }

        public void setInvoiceNo(String invoiceNo) {
            this.invoiceNo = invoiceNo;
        }

        public String getCashier() {
            return cashier;
        }

        public void setCashier(String cashier) {
            this.cashier = cashier;
        }

        public List<String> getItems() {
            return items;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }
    }
}