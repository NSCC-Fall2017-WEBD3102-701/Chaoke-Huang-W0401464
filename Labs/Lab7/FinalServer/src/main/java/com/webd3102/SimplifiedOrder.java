package com.webd3102;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SimplifiedOrder {
    private int id;
    String userName;
    private LocalDate date;
    private List<String[]> purchases= new ArrayList<>();

    public SimplifiedOrder(int id, String userName, LocalDate date, List<String[]> purchases) {
        this.id = id;
        this.userName = userName;
        this.date = date;
        this.purchases = purchases;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<String[]> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<String[]> purchases) {
        this.purchases = purchases;
    }
}
