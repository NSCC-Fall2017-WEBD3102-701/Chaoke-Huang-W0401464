package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "purchase")
@ViewScoped
public class Purchase {
    private int id;

    private double price;
    private int amount = 1;
    private double subtotal;

    private Order order;
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Purchase() {
        super();
    }

    public Purchase(Product product) {
        this.price = product.getPrice();
        this.amount = 1;
        this.subtotal = price * amount;
        this.product = product;
    }

    public Purchase(Product product, int amount) {
        this.price = product.getPrice();
        this.amount = amount;
        this.subtotal = price * amount;
        this.product = product;
    }

    public void addAmount() {
        this.setAmount(this.getAmount() + 1);
        return;
    }

    public void subAmount() {
        if (this.getAmount() >= 2) {
            this.setAmount(this.getAmount() - 1);
        }
        return;
    }
}