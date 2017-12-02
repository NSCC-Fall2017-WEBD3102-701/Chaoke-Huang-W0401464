package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "orderService")
@SessionScoped
public class OrderService {
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderService() throws Exception {
        super();
        order = new Order();
       // userDBUtil = UserDBUtil.getInstance();

    }

    public String addPurchase(Product product, int amount){
        Purchase purchase = new Purchase(product, amount);
        order.getPurchases().add(purchase);
        order.setTotal(order.getTotal()+purchase.getSubtotal());
        return "cart.xhtml?faces-redirect=true";
    }



    public void subPurchase(Purchase purchase){
        order.getPurchases().remove(purchase);
        order.setTotal(order.getTotal()- purchase.getSubtotal());
        return;
    }

    public String addPurchase(Product product){
        Purchase purchase = new Purchase(product);
        order.getPurchases().add(purchase);
        order.setTotal(order.getTotal()+purchase.getSubtotal());
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
            order.getPurchases().remove(purchase);
            return "cart.xhtml";
        }
        purchase.setAmount(purchase.getAmount() - 1);
        purchase.setSubtotal(purchase.getProduct().getPrice()*purchase.getAmount());
        updateTotal();
        return "cart.xhtml?faces-redirect=true";
    }

    public void updateTotal(){
        double total=0;
        for (Purchase  pur: order.getPurchases()
                ) {

            total = total + pur.getSubtotal();
        }

        order.setTotal(total);
        return;

    }



}
