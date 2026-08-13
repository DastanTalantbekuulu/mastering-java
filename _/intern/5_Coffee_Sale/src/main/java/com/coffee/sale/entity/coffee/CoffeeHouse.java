package com.coffee.sale.entity.coffee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coffee_houses")
public class CoffeeHouse {

    @Id
    @Column(name = "store_id", unique = true, updatable = false)
    private Integer storeId;

    @Column(name = "city")
    private String city;

    @Column(name = "coffee")
    private Integer coffee;

    @Column(name = "merch")
    private Integer merch;

    @Column(name = "total")
    private Integer total;

    public CoffeeHouse() {
    }

    public CoffeeHouse(Integer storeId, String city, Integer coffee, Integer merch) {
        this.storeId = storeId;
        this.city = city;
        this.coffee = coffee;
        this.merch = merch;
        this.total = coffee + merch;
    }

    public CoffeeHouse(Integer storeId, String city, Integer coffee, Integer merch, Integer total) {
        this.storeId = storeId;
        this.city = city;
        this.coffee = coffee;
        this.merch = merch;
        this.total = total;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCoffee() {
        return coffee;
    }

    public void setCoffee(Integer coffee) {
        this.coffee = coffee;
    }

    public Integer getMerch() {
        return merch;
    }

    public void setMerch(Integer merch) {
        this.merch = merch;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}