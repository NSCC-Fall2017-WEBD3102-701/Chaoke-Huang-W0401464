package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "order")
public class Order {
    private int id;
    private LocalDate date;
    private List<Purchase> purchases = new ArrayList<>();
    private User user;
    private double total = 0;

    public Order() {
        super();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UserService userService
                = (UserService) facesContext.getApplication()
                .createValueBinding("#{userService}").getValue(facesContext);
        user = userService.user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void resetOrder() {
        this.purchases.clear();
        this.total = 0;
    }
}
