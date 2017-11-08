package com.webd3102;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SessionScoped
@ManagedBean(name = "invoiceBean")

public class InvoiceBean {
    private final DateFormat myDate = new SimpleDateFormat("MM/dd/yyyy");
    private final String baseUrl = "http://localhost:8080/myapp/invoices/";
    private final String getAllUrl = "invoices";
    private final String getUrl = "invoice";
    private final String updateUrl = "update";
    private final String lastIDUrl = "lastID";
    private final String deleteUrl = "delete";
    private final String addInvoiceUrl = "addInvoice";

    List<Invoice> invoiceList;
    Invoice invoiceModel = new Invoice();
    Date dueDate;
    Date orderDate;

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public List<Invoice> getInvoiceList() {
        try {
            getAllInvoiceRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return invoiceList;
    }

    public Invoice getInvoiceModel() {
        return invoiceModel;
    }

//    public Invoice getEditableInvoice() {
//        return editableInvoice;
//    }
//
//    public void setEditableInvoice(Invoice editableInvoice) {
//        this.editableInvoice = editableInvoice;
//    }
//
//    public String getInvoiceCashierInput() {
//        return invoiceCashierInput;
//    }
//
//    public void setInvoiceCashierInput(String invoiceCashierInput) {
//        this.invoiceCashierInput = invoiceCashierInput;
//    }
//
//    public List<String> getInvoiceItemsInput() {
//        return invoiceItemsInput;
//    }
//
//    public void setInvoiceItemsInput(List<String> invoiceItemsInput) {
//        this.invoiceItemsInput = invoiceItemsInput;
//    }
//
//    public Date getInvoiceDueDateInput() {
//        return invoiceDueDateInput;
//    }
//
//    public void setInvoiceDueDateInput(Date invoiceDueDateInput) {
//        this.invoiceDueDateInput = invoiceDueDateInput;
//    }
//
//    public Date getInvoiceOrderDateInput() {
//        return invoiceOrderDateInput;
//    }
//
//    public void setInvoiceOrderDateInput(Date invoiceOrderDateInput) {
//        this.invoiceOrderDateInput = invoiceOrderDateInput;
//    }
//
//    public List<Invoice> getList() {
//        list.clear();
//        List<String> items1 = new ArrayList<String>();
//        items1.add("OnePlus5");
//        List<String> items2 = new ArrayList<String>();
//        items2.add("OnePlus5");
//        items2.add("IPhone X");
//        list.add(invoice1);
//        list.add(invoice2);
//        return list;
//    }
//
//    public void setList(List<Invoice> list) {
//        this.list = list;
//    }
//
//
//    public Invoice getInvoiceRequest() throws IOException {
//        GetRequest request = new GetRequest();
//        String response = request.run("/invoice{invoiceNo}");
//        Gson gson = new Gson();
//        Invoice invoice = gson.fromJson(response, Invoice.class);
//        return invoice;
//    }

    public String updateInvoiceRequest() throws IOException {
        invoiceModel.setOrderDate(myDate.format(orderDate));
        invoiceModel.setDueDate(myDate.format(dueDate));
        Gson gson = new Gson();
        String invoiceJson = gson.toJson(invoiceModel);
        Requests.Post(baseUrl + updateUrl, invoiceJson );
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", "#" + invoiceModel.getInvoiceNo() + " invoice was successfully updated");
        return "invoices?faces-redirect=true";
    }

    public String editInvoice(Invoice invoice) throws IOException, ParseException {
        invoiceModel.Copy(invoice);
        orderDate = myDate.parse(invoice.getOrderDate());
        dueDate = myDate.parse(invoice.getDueDate());
       return "updateInvoice?faces-redirect=true";
    }

    private void getAllInvoiceRequest() throws IOException {
        String response = Requests.Get(baseUrl + getAllUrl);
        Gson gson = new Gson();
        invoiceList = gson.fromJson(response, new TypeToken<List<Invoice>>() {
        }.getType());
    }

    public String goAddinvoicePage() throws IOException {
        invoiceModel.Reset();
        orderDate = null;
        dueDate = null;
        return"addInvoice?faces-redirect=true";
    }

    public String addInvoice() throws IOException {
        invoiceModel.setOrderDate(myDate.format(orderDate));
        invoiceModel.setDueDate(myDate.format(dueDate));
        invoiceModel.setInvoiceNo(getLastIDRequest());
        Gson gson = new Gson();
        String invoiceJson = gson.toJson(invoiceModel);

        Requests.Put(baseUrl + addInvoiceUrl, invoiceJson);

        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", "#" + invoiceModel.getInvoiceNo() + " was successfully added");
        return "invoices?faces-redirect=true";
    }

    private String getLastIDRequest() throws IOException {
        String response = Requests.Get(baseUrl + lastIDUrl);
        Gson gson = new Gson();
        return gson.fromJson(response, String.class);
    }

    public String deleteInvoice(String invoiceNo) throws IOException {
        Requests.Delete(baseUrl + deleteUrl + invoiceNo);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", "#" + invoiceNo + " invoice was successfully deleted");
        return "invoices?faces-redirect=true";
    }
}