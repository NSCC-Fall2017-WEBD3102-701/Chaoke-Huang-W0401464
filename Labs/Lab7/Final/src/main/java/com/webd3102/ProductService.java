package com.webd3102;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Scope;
import javax.print.attribute.standard.PrinterURI;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@ManagedBean(name="productService")
public class ProductService {
    private List<Product> products = new ArrayList<>();
    private HttpClient httpClient = new HttpClient();
    private ProductDBUtil productDBUtil;
    private static Product currentProduct;




    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }


    public ProductService() throws Exception {
        super();
        productDBUtil = ProductDBUtil.getInstance();

    }

    public List<Product> getProducts() throws Exception {
        products = productDBUtil.getProducts();
        return products;
    }

    public String goProductDetail(Product product){
        //System.out.println(product.getName());
        this.setCurrentProduct(product);
       // System.out.println(product.getId());
        return "productDetail.xhtml?faces-redirect=true";

    }



}
