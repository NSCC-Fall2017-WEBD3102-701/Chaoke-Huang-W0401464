package oldclasses;

import javax.faces.bean.ManagedBean;


@ManagedBean(name = "invoice")
public class Invoice implements Comparable<Invoice>{
    private String name;
    private Integer id;
    private String[] items;
    private String orderDate;
    private String dueDate;

    public Invoice() { }
    public Invoice(Integer id, String name, String orderDate, String dueDate, String[] items) {
        this.id = id;
        this.name = name;
        this.orderDate = orderDate;
        this.dueDate = dueDate;
        this.items = items;
    }

    public Invoice(String name, String orderDate, String dueDate, String[] items) {
        this.name = name;
        this.orderDate = orderDate;
        this.dueDate = dueDate;
        this.items = items;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public int getItemsLength() {
        if (this.items != null) {
            return this.items.length;
        } else {
            return 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int compareTo(Invoice o) {
        return this.getId().compareTo(o.getId());
    }
}
