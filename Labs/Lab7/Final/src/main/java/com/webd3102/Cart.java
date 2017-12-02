package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.List;

@ManagedBean(name = "cart")
@SessionScoped
public class Cart {
    private List<Purchase> purchases;
    private double total;
    public Cart() {
        super();
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void addPurchase(Purchase purchase){
        purchases.add(purchase);
        return;
    }

    public void subPurchase(Purchase purchase){
        purchases.remove(purchase);
        return;
    }
}
