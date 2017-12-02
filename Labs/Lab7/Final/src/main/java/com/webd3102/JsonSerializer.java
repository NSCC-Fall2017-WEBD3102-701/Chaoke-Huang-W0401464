package com.webd3102;

import com.google.gson.Gson;
import oldclasses.Invoice;

public class JsonSerializer<T> {

    private Gson gson = new Gson();

    public String serializeInvoice(Invoice i) {
        String serialized = gson.toJson(i, Invoice.class);
        return serialized;
    }
}
