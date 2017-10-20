package com.webd3102;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xerces.internal.xs.StringList;
import okhttp3.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "invoice")
@SessionScoped
public class InvoiceBean {
    List<Invoice> list = new ArrayList<Invoice>();
    Invoice editableInvoice = new Invoice();
    String invoiceCashierInput;
    List<String> invoiceItemsInput;
    Date invoiceDueDateInput;
    Date invoiceOrderDateInput;
    private OkHttpClient client = new OkHttpClient();

    public Invoice getEditableInvoice() {
        return editableInvoice;
    }

    public void setEditableInvoice(Invoice editableInvoice) {
        this.editableInvoice = editableInvoice;
    }

    public String getInvoiceCashierInput() {
        return invoiceCashierInput;
    }

    public void setInvoiceCashierInput(String invoiceCashierInput) {
        this.invoiceCashierInput = invoiceCashierInput;
    }

    public List<String> getInvoiceItemsInput() {
        return invoiceItemsInput;
    }

    public void setInvoiceItemsInput(List<String> invoiceItemsInput) {
        this.invoiceItemsInput = invoiceItemsInput;
    }

    public Date getInvoiceDueDateInput() {
        return invoiceDueDateInput;
    }

    public void setInvoiceDueDateInput(Date invoiceDueDateInput) {
        this.invoiceDueDateInput = invoiceDueDateInput;
    }

    public Date getInvoiceOrderDateInput() {
        return invoiceOrderDateInput;
    }

    public void setInvoiceOrderDateInput(Date invoiceOrderDateInput) {
        this.invoiceOrderDateInput = invoiceOrderDateInput;
    }

    public List<Invoice> getList() {
        list.clear();
        List<String> items1 = new ArrayList<String>();
        items1.add("OnePlus5");
        List<String> items2 = new ArrayList<String>();
        items2.add("OnePlus5");
        items2.add("IPhone X");
        Invoice invoice1 = new Invoice("1", "CK Huang", items1, "11/11/2017", "12/12/2017");
        Invoice invoice2 = new Invoice("2", "Derrick Ma", items2, "12/12/2017", "01/12/2018");
        list.add(invoice1);
        list.add(invoice2);
        return list;
    }

    public void setList(List<Invoice> list) {
        this.list = list;
    }

    public List<Invoice> getAllInvoiceRequest() throws IOException {
        GetRequest request = new GetRequest();
        String response = request.run("http://localhost:8080/myapp/invoices/invoices");
        Gson gson = new Gson();
        list = gson.fromJson(response, new TypeToken<List<Invoice>>() {
        }.getType());
        return list;
    }

    public Invoice getInvoiceRequest() throws IOException {
        GetRequest request = new GetRequest();
        String response = request.run("/invoice{invoiceNo}");
        Gson gson = new Gson();
        Invoice invoice = gson.fromJson(response, Invoice.class);
        return invoice;
    }

    public void deleteInvoice(String invoiceNo) throws IOException {
        PostRequest postDel = new PostRequest();
        postDel.post("http://localhost:8080/myapp/invoices/delete" + invoiceNo, "");
    }

    public void addInvoice() throws IOException {
        PostRequest postAdd = new PostRequest();
        String invoiceNoInput = getNewID();
        DateFormat myDate = new SimpleDateFormat("MM/dd/yyyy");
        String invoiceOrderDate = myDate.format(invoiceOrderDateInput);
        String invoiceDueDate = myDate.format(invoiceDueDateInput);
        Invoice invoice = new Invoice(invoiceNoInput, invoiceCashierInput, invoiceItemsInput, invoiceOrderDate, invoiceDueDate);
        Gson gson = new Gson();
        String invoiceJson = gson.toJson(invoice);
        postAdd.post("http://localhost:8080/myapp/invoices/addInvoice", invoiceJson);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect("http://localhost:8000/invoices.xhtml");
    }

    public void updateInvoiceRequest() throws IOException {
        PostRequest postAdd = new PostRequest();

        DateFormat myDate = new SimpleDateFormat("MM/dd/yyyy");
        String invoiceOrderDate = myDate.format(invoiceOrderDateInput);
        String invoiceDueDate = myDate.format(invoiceDueDateInput);
        editableInvoice.orderDate = myDate.format(invoiceOrderDateInput);
        editableInvoice.dueDate = myDate.format(invoiceDueDateInput);
        Gson gson = new Gson();
        String invoiceJson = gson.toJson(editableInvoice);
        postAdd.post("http://localhost:8080/myapp/invoices/update", invoiceJson);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect("http://localhost:8000/invoices.xhtml");


    }

    public String getNewID() throws IOException {

        getAllInvoiceRequest();
        int maxID = 0;
        for (Invoice invoice : list
                ) {
            if (Integer.parseInt(invoice.invoiceNo) >= maxID) {
                maxID = Integer.parseInt(invoice.invoiceNo);

            }

        }
        return Integer.toString(maxID + 1);

    }

    public void editInvoice(Invoice invoice) throws IOException {

        editableInvoice = invoice;
        editableInvoice.items.clear();

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect("http://localhost:8000/updateInvoice.xhtml");
    }

    public static class Invoice {
        String invoiceNo;
        String cashier;
        List<String> items = new ArrayList<>();
        String orderDate;
        String dueDate;

        public Invoice() {
        }

        public Invoice(String invoiceNo, String cashier, List<String> items, String orderDate, String dueDate) {
            this.cashier = cashier;
            this.invoiceNo = invoiceNo;
            this.items = items;
            this.orderDate = orderDate;
            this.dueDate = dueDate;
        }

        public String getCashier() {
            return cashier;
        }

        public void setCashier(String cashier) {
            this.cashier = cashier;
        }

        public void setItems(List<String> items) {
            this.items = items;
        }

        //getter and setter methods

        public String getInvoiceNo() {
            return invoiceNo;
        }

        public void setInvoiceNo(String invoiceNo) {
            this.invoiceNo = invoiceNo;
        }

        public List<String> getItems() {
            return items;
        }

        public void setItems(StringList items) {
            this.items = items;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getDueDate() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate = dueDate;
        }
    }

    private class GetRequest {
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }
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
