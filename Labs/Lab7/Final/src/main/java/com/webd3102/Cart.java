package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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

    public String addPurchase(Product product, int amount){
        Purchase purchase = new Purchase(product, amount);
        purchases.add(purchase);
        this.setTotal(this.getTotal()+purchase.getSubtotal());
        return "cart.xhtml?faces-redirect=true";
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

    public String addAmount(Purchase purchase){

        purchase.setAmount(purchase.getAmount() + 1);
        purchase.setSubtotal(purchase.getProduct().getPrice()*purchase.getAmount());
        updateTotal();
        return "cart.xhtml?faces-redirect=true";
    }

    public String subAmount(Purchase purchase){
        if (purchase.getAmount() - 1 < 0) {
            purchases.remove(purchase);
            return "cart.xhtml";
        }
        purchase.setAmount(purchase.getAmount() - 1);
        purchase.setSubtotal(purchase.getProduct().getPrice()*purchase.getAmount());
        updateTotal();
        return "cart.xhtml?faces-redirect=true";
    }

    public void updateTotal(){
        double total=0;
        for (Purchase  pur: purchases
             ) {

            total = total + pur.getSubtotal();
        }

        setTotal(total);
        return;

    }

}
