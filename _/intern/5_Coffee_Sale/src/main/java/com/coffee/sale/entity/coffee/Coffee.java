package com.coffee.sale.entity.coffee;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;


@Entity
@Table(name = "coffees")
public class Coffee {
    @EmbeddedId
    private CoffeeId id;

    @Column(name = "price")
    private Float price;

    @Column(name = "sales", columnDefinition = "integer default 0")
    private Integer sales;

    @Column(name = "total", columnDefinition = "integer default 0")
    private Integer total;

    @ManyToOne
    @MapsId("sup_id")
    @JoinColumn(name = "sup_id", referencedColumnName = "sup_id", updatable = false)
    private Supplier supplier;

    public Coffee() {
    }

    public Coffee(CoffeeId id, Float price, Integer sales, Integer total, Supplier supplier) {
        this.id = id;
        this.price = price;
        this.sales = sales;
        this.total = total;
        this.supplier = supplier;
    }

    public CoffeeId getId() {
        return id;
    }

    public void setId(CoffeeId id) {
        this.id = id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Coffee{" +
               "id=" + id +
               ", price=" + price +
               ", sales=" + sales +
               ", total=" + total +
               ", supplier=" + supplier +
               '}';
    }
}