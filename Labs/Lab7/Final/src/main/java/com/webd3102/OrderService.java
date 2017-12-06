package com.webd3102;

import com.google.gson.Gson;
import okhttp3.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "orderService")
@SessionScoped
public class OrderService {
    private Order order;
    private OrderDBUtil orderDBUtil;
    private OkHttpClient client = new OkHttpClient();


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
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", product.getName() + "(s) added to the cart!");
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
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", product.getName() + "(s) added to the cart!");
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
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", purchase.getProduct().getName() + " has been removed from the cart!");
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

    public String goCheckOut(User user) throws Exception {
        if (order.getPurchases().size() == 0) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", "The cart is empty, please enjoy your purchase in Sleeping Well.");
            return "sleep.xhtml:faces-redirect=true";
        }
        order.setDate(LocalDate.now());
        User latestuser = new User();
        latestuser = UserDBUtil.getInstance().getUser(user.getUser_name());
        order.getUser().setBalance(latestuser.getBalance());
        return "checkout.xhtml:faces-redirect=true";
    }

    public String confirmCheckout() throws Exception {
        if (order.getUser().getBalance() < order.getTotal()) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", "You don't have enough balance to finish the purchase.");
            return "checkout.xhtml:faces-redirect=true";
        } else {
            orderDBUtil.addOrder(order);
            SimplifiedOrder simplifiedOrder = converSimplifiiedOrder(order);

            PostRequest postAdd = new PostRequest();
            Gson gson = new Gson();
            String orderJson = gson.toJson(simplifiedOrder);
            postAdd.post("http://localhost:8000/myapp/show",orderJson);

            order.getUser().setBalance(order.getUser().getBalance() - order.getTotal());
            UserDBUtil.getInstance().updateUser(order.getUser());
            order.resetOrder();
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", "Congrats to your purchase!");
            return "sleep.xhtml?faces-redirect=true";
        }
    }


    public SimplifiedOrder converSimplifiiedOrder(Order order) {
        List<String[]> simplePurchases = new ArrayList<>();
        for (Purchase pur : order.getPurchases()) {
            String[] simplePurcase = new String[4];


            simplePurcase[0] = pur.getProduct().getName();
            simplePurcase[1] = Integer.toString(pur.getAmount());
            simplePurcase[2] = Double.toString(pur.getSubtotal());
            simplePurcase[3] = Double.toString(pur.getPrice());

            simplePurchases.add(simplePurcase);

        }

        SimplifiedOrder simplifiedOrder = new SimplifiedOrder(order.getId(), order.getUser().getUser_name(), order.getDate(), simplePurchases);
        return simplifiedOrder;


    }


    private class PostRequest {
        public final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        String post(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }
    }


}
