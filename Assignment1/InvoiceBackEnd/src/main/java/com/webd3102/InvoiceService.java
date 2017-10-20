package com.webd3102;

import com.google.gson.Gson;
import com.webd3102.InvoiceDAO.Invoice;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("invoices")
public class InvoiceService {

    @POST
    @Path("/update")
    @Consumes({MediaType.APPLICATION_JSON})
    public Invoice updateInvoice_JSON(String invoiceStr) {

        Gson gson = new Gson();

        return InvoiceDAO.updateInvoice(gson.fromJson(invoiceStr, Invoice.class));
    }

    @GET
    @Path("/invoices")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Invoice> getAllInvoices_JSON() {
        List<Invoice> list = InvoiceDAO.getAllInvoices();
        return list;
    }

    @GET
    @Path("/invoice{invoiceNo}")
    @Produces({MediaType.APPLICATION_JSON})
    public Invoice getInvoice(@PathParam("invoiceNo") String invoiceNo) {
        return InvoiceDAO.getInvoice(invoiceNo);
    }


    @POST
    @Path("/delete{invoiceNo}")
    @Produces({MediaType.APPLICATION_JSON})
    public void deleteInvoice(@PathParam("invoiceNo") String invoiceNo) {
        InvoiceDAO.deleteInvoice(invoiceNo);
    }

    @POST
    @Path("/addInvoice")
    @Consumes({MediaType.APPLICATION_JSON})
    public Invoice addInvoice(String invoiceStr) {
        Gson gson = new Gson();
        return InvoiceDAO.addInvoice(gson.fromJson(invoiceStr, Invoice.class));
    }
}
