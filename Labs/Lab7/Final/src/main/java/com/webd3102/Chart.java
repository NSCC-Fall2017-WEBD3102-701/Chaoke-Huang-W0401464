package com.webd3102;


import org.primefaces.model.chart.PieChartModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "chart")
public class Chart implements Serializable {
    Date before;
    Date after;
    Map<String, String> dataMap;
    private PieChartModel pieModel;

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public void setPieModel(PieChartModel pieModel) {
        this.pieModel = pieModel;
    }

    public Date getBefore() {
        return before;
    }

    public void setBefore(Date before) {
        this.before = before;
    }

    public Date getAfter() {
        return after;
    }

    public void setAfter(Date after) {
        this.after = after;
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }


    public String createPieModel() throws Exception {
        getAnaData(before,after);
        pieModel = new PieChartModel();

        for (Map.Entry<String, String> entry : dataMap.entrySet()) {

            pieModel.set(entry.getKey(), Integer.parseInt(entry.getValue()));


        }

        pieModel.setTitle("Report Chart");
        pieModel.setLegendPosition("w");

        return "admin_orders_analyse.xhtml";

    }

    public void getAnaData(Date before, Date after) throws Exception {
        System.out.println("begin collect data");
        List<Order> orders;
        LocalDate localbefore = before.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localafter = after.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        orders = OrderDBUtil.getInstance().getOrderByDate(localbefore, localafter);

        Map<String, String> myMap = new HashMap<>();
        String productName;


        for (Order order : orders
                ) {

            for (Purchase purchase : order.getPurchases()
                    ) {

                productName = purchase.getProduct().getName();
                if (myMap.containsKey(productName)) {

                    myMap.put(productName, Integer.toString(Integer.parseInt(myMap.get(productName)) + purchase.getAmount()));

                } else {
                    myMap.put(productName, Integer.toString(purchase.getAmount()));
                }

            }


        }

        dataMap = myMap;
        return;
    }


}