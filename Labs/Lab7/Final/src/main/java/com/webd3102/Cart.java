package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "cart")
@SessionScoped
public class Cart {
    private List<Purchase> purchases = new ArrayList<>();
    private double total=0;


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

    public void addPurchase(Product product, int amount){
        Purchase purchase = new Purchase(product, amount);
        purchases.add(purchase);
        this.setTotal(this.getTotal()+purchase.getSubtotal());
        return;
    }

    public void subPurchase(Purchase purchase){
        purchases.remove(purchase);
        this.setTotal(this.getTotal()- purchase.getSubtotal());
        return;
    }

    public String addPurchase(Product product){
        Purchase purchase = new Purchase(product);
        purchases.add(purchase);
        this.setTotal(this.getTotal()+purchase.getSubtotal());
        return "cart.xhtml?faces-redirect=true";
    }

}
