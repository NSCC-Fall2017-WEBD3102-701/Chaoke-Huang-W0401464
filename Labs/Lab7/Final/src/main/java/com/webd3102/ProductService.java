package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "productService")
@SessionScoped
public class ProductService {

    private static Product currentProduct;
    private List<Product> products = new ArrayList<>();
    private List<Product> searchProducts = new ArrayList<>();
    private String searchStr;
    private HttpClient httpClient = new HttpClient();
    private ProductDBUtil productDBUtil;


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

    public List<Product> getSearchProducts() {
        return searchProducts;
    }

    public void setSearchProducts(List<Product> searchProducts) {
        this.searchProducts = searchProducts;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    public List<Product> getProducts() throws Exception {
        products = productDBUtil.getProducts();
        return products;
    }

    public String goProductDetail(Product product) {
        this.setCurrentProduct(product);
        return "productDetail.xhtml?faces-redirect=true";
    }


    public String showSearch() throws Exception {
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        String searchStr = ec.getRequestParameterMap().get("search:inputStr");
        List<Product> lastestProducts = getProducts();
        List<Product> tempProducts = new ArrayList<>();
        for (Product product : lastestProducts) {

            if (product.getName().toLowerCase().contains(searchStr.toLowerCase())) {

                tempProducts.add(product);
            }

        }
        this.setSearchProducts(tempProducts);
        return "sleepSearch.xhtml?faces-redirect=true";
    }


    public String updateProduct(Product product) {
        productDBUtil.updateProduct(product);
        return "sleep.xhtml?faces-redirect=true";
    }

}
