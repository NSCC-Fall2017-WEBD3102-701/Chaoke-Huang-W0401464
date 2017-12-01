package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean(name = "purchase")
@ViewScoped
public class Purchase {
    private Product product;
    private int amount;
    private double subtotal;


    public Purchase() {
        super();
    }


    public Product getProduct() {
        return product;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void addAmount() {

        this.setAmount(this.getAmount() + 1);
        return;
    }

    public void subAmount() {
        if (this.getAmount() >= 1) {
            this.setAmount(this.getAmount() - 1);
        }
        return;
    }

}
