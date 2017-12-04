package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "orderService")
@SessionScoped
public class OrderService {
    private Order order;
    private OrderDBUtil orderDBUtil;

    public OrderService() throws Exception {
        super();
        order = new Order();
        orderDBUtil = OrderDBUtil.getInstance();

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

    public List<Order> getOrdersByUser(User user) {
        List<Order> orders = new ArrayList<>();

        orders = orderDBUtil.getOrdersByUser(user);


        return orders;

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

    public String goCheckOut(User user) {
        if (order.getPurchases().size() == 0) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", "The cart is empty, please enjoy your purchase in Sleeping Well.");
            return "sleep.xhtml:faces-redirect=true";
        }
        order.setDate(LocalDate.now());
        order.setUser(user);

        return "checkout.xhtml:faces-redirect=true";
    }

    public String confirmCheckout() {
        if (order.getUser().getBalance() < order.getTotal()) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", "You don't have enough balance to finish the purchase.");
            return "checkout.xhtml:faces-redirect=true";
        } else {
            orderDBUtil.addOrder(order);
            order.resetOrder();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", "Congrats to your purchase!");
            return "sleep.xhtml?faces-redirect=true";
        }
    }
}
