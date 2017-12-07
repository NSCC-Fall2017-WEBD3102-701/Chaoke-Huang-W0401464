package com.webd3102.Old;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Path("/invoices")
public class InvoiceService {
    private static HashMap<String, Invoice> invoices = new HashMap<>();
    private static int lastId = 0;

    public InvoiceService() {
        if (invoices.isEmpty()) {
            lastId++;
            invoices.put(Integer.toString(lastId), new Invoice(lastId, "Test"));
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Invoice> getInvoices() {
        return getSortedInvoices();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInvoice(Invoice invoice) {
        lastId++;
        invoice.setId(lastId);
        invoices.put(Integer.toString(lastId), invoice);
        return Response.status(201).entity("OK").build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteInvoice(@PathParam("id") String id) {
        Invoice toDel = invoices.get(id);
        if (toDel != null) {
            invoices.remove(id);
            return Response.status(200).entity("OK").build();
        } else {
            return Response.status(404).entity("").build();
        }
    }

    @Path("{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createItem(Invoice invoice, @PathParam("id") String id) {
        Invoice replace = invoices.get(id);
        if (replace != null) {
            invoices.put(id, invoice);
            return Response.status(200).entity("OK").build();
        } else {
            return Response.status(404).entity("").build();
        }
    }

    private List<Invoice> getSortedInvoices() {
        List<Invoice> values = new ArrayList<>(invoices.values());
        Collections.sort((List<Invoice>) values);
        return values;
    }
}
