package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@ManagedBean(name = "orderService")
@SessionScoped
public class OrderService {
    private Order order;

    public OrderService() throws Exception {
        super();
        order = new Order();
        // userDBUtil = UserDBUtil.getInstance();

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String addPurchase(Product product, int amount) {
        Purchase purchase = new Purchase(product, amount);
        order.getPurchases().add(purchase);
        order.setTotal(order.getTotal() + purchase.getSubtotal());
        return "cart.xhtml?faces-redirect=true";
    }


    public void subPurchase(Purchase purchase) {
        order.getPurchases().remove(purchase);
        order.setTotal(order.getTotal() - purchase.getSubtotal());
        return;
    }

    public String addPurchase(Product product) {
        Purchase purchase = new Purchase(product);
        order.getPurchases().add(purchase);
        order.setTotal(order.getTotal() + purchase.getSubtotal());
        return "cart.xhtml?faces-redirect=true";
    }

    public String addAmount(Purchase purchase) {

        purchase.setAmount(purchase.getAmount() + 1);
        purchase.setSubtotal(purchase.getProduct().getPrice() * purchase.getAmount());
        updateTotal();
        return "cart.xhtml?faces-redirect=true";
    }

    public String subAmount(Purchase purchase) {
        if (purchase.getAmount() - 1 == 0) {
            order.getPurchases().remove(purchase);
            updateTotal();
            return "cart.xhtml";
        }
        purchase.setAmount(purchase.getAmount() - 1);
        purchase.setSubtotal(purchase.getProduct().getPrice() * purchase.getAmount());
        updateTotal();
        return "cart.xhtml?faces-redirect=true";
    }

    public void updateTotal() {
        double total = 0;
        for (Purchase pur : order.getPurchases()
                ) {

            total = total + pur.getSubtotal();
        }

        order.setTotal(total);
        return;
    }

    public String goCheckOut() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Date currentDate = new Date();
        order.setDate(currentDate);
        return "checkout.xhmtl:faces-redirect=true";
    }


}
