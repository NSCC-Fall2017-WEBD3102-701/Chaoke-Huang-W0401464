package com.webd3102;

import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "invoice")
public class Invoice implements Comparable<Invoice>{
    private String name;
    private Integer id;
    private String orderDate;
    private String dueDate;
    private String[] items;

    public Invoice() { }
    public Invoice(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    @Override
    public int compareTo(Invoice o) {
        return this.getId().compareTo(o.getId());
    }
}
