package com.webd3102;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="user")
@SessionScoped
public class User {

    private int id;
    private String first_name;
    private String last_name;
    private String user_name;
    private double balance;
    private String email;
    private String password;


    public User(){}

    public User(int id, String first_name,String last_name,String user_name, double balance, String email, String password){
        this.id = id;
        this.first_name = first_name;
        this.last_name =last_name;
        this.user_name = user_name;
        this.balance = balance;
        this.email = email;
        this.password = password;
    }


    public User(String first_name,String last_name,String user_name, double balance, String email, String password){
        this.first_name = first_name;
        this.last_name =last_name;
        this.user_name = user_name;
        this.balance = balance;
        this.email = email;
        this.password = password;
    }

    public User(String first_name,String last_name,String user_name, String email, String password){
        this.first_name = first_name;
        this.last_name =last_name;
        this.user_name = user_name;
        this.balance = 1000;
        this.email = email;
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





    }
