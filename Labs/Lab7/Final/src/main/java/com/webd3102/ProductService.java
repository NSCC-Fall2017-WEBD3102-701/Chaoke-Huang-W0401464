package com.webd3102;

import oldclasses.HttpClient;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "productService")
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private HttpClient httpClient = new HttpClient();
    private ProductDBUtil productDBUtil;
    private String searchStr;

    public ProductService() throws Exception {
        super();
        productDBUtil = ProductDBUtil.getInstance();

    }


    public String getSearchStr() {
        return searchStr;
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }


//   // public Product getCurrentProduct() {
//        return currentProduct;
//    }
//
//    public void setCurrentProduct(Product currentProduct) {
//        this.currentProduct = currentProduct;
//    }

    public List<Product> getProducts() throws Exception {
        products = productDBUtil.getAliveProducts();
        return products;
    }

//    public String goProductDetail(Product product) {
//        this.setCurrentProduct(product);
//        return "productDetail.xhtml?faces-redirect=true";
//    }

    public String addProduct(Product product) {

        productDBUtil.addProduct(product);

        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", product.getName() + " has been added to the our offerings!!");
        return "sleep.xhtml?faces-redirect=true";
    }


    public List<Product> getDeadProducts() throws Exception {

        return productDBUtil.getDeadProducts();


    }

    public String updateProduct(Product product) {
        productDBUtil.updateProduct(product);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("feedback", product.getName() + " successfully modified!");
        return "sleep.xhtml?faces-redirect=true";
    }


    public String addBackToPage(Product product) {

        product.setSoftDeleted(0);
        productDBUtil.updateProduct(product);

        return "sleep.xhtml?faces-redirect=true";
    }

    public String removeFromPage(Product product) {

        product.setSoftDeleted(1);
        productDBUtil.updateProduct(product);

        return "sleep.xhtml?faces-redirect=true";
    }


    public List<Product> getAliveProductsWithSearch(String searchStr) throws Exception {

        List<Product> allProducts = productDBUtil.getProducts();

        List<Product> searchProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(searchStr.toLowerCase()) && product.getSoftDeleted() == 0) {
                searchProducts.add(product);

            }
        }
        return searchProducts;
    }

    public List<Product> getDeadProductsWithSearch(String searchStr) throws Exception {

        List<Product> allProducts = productDBUtil.getProducts();

        List<Product> searchProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(searchStr.toLowerCase()) && product.getSoftDeleted() == 1) {
                searchProducts.add(product);

            }
        }
        return searchProducts;
    }




}
