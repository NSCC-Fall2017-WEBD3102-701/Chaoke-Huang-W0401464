package com.webd3102;

import javax.faces.bean.ManagedBean;


//@XmlRootElement(name = "product")
@ManagedBean(name = "product")
public class Product implements Comparable<Product> {
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String pic_ref;


    public Product() {
    }

    public Product(Integer id, String name, Double price, String discription, String pic_ref) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = discription;
        this.pic_ref = pic_ref;

    }

    public Product(String name, Double price, String discription, String pic_ref) {
        this.name = name;
        this.price = price;
        this.description = discription;
        this.pic_ref = pic_ref;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic_ref() {
        return pic_ref;
    }

    public void setPic_ref(String pic_ref) {
        this.pic_ref = pic_ref;
    }

    @Override
    public int compareTo(Product o) {
        return this.getId().compareTo(o.getId());
    }
}
