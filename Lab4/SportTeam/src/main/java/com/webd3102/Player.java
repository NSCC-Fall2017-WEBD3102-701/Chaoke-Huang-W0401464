package com.webd3102;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;

@ManagedBean(name = "player")
@SessionScoped
public class Player {
    private String name;
    private int age;
    private boolean feesPaid;
    private String zip;
    private Date dob;
    private double height;
    private SinData sin;


    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public SinData getSin() {
        return sin;
    }

    public void setSin(SinData sin) {
        this.sin = sin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isFeesPaid() {
        return feesPaid;
    }

    public void setFeesPaid(boolean feesPaid) {
        this.feesPaid = feesPaid;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    private void formatZip() {
        if (!this.zip.contains(" ")) {
            StringBuilder sb = new StringBuilder(this.zip);
            sb.insert(3, " ");
            this.zip = sb.toString().toUpperCase();

        } else {
            this.zip = this.zip.toUpperCase();
        }
    }

    public String goDisplayPlayer() {
        formatZip();
        if (this.age >= 12) {
            return "division1?faces-redirect=true";
        } else {
            return "division2?faces-redirect=true";
        }


    }
}
