package com.webd3102;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Invoice implements Serializable {
    private String invoiceNo;
    private String cashier;
    private List<String> items = new ArrayList<>();
    private String orderDate;
    private String dueDate;

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

    public void Reset()    {
        this.invoiceNo = null;
        this.cashier = null;
        this.orderDate = null;
        this.dueDate = null;
        this.items.clear();
    }

    public void Copy(Invoice invoice)
    {
        this.invoiceNo = invoice.invoiceNo;
        this.cashier = invoice.cashier;
        this.orderDate = invoice.orderDate;
        this.dueDate = invoice.dueDate;
        this.items.clear();
        this.items.addAll(invoice.items);
    }
}
