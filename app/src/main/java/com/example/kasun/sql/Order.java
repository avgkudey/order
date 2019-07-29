package com.example.kasun.sql;

public class Order {
    int id;
    String name,amount,customer;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order(int id, String name, String amount, String customer) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Order() {
    }


}
