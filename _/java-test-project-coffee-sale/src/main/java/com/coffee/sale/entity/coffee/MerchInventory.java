package com.coffee.sale.entity.coffee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "merch_inventory")
public class MerchInventory {
    @Id
    @Column(name = "item_id")
    private Integer id;

    @Column(name = "item_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "sup_id", referencedColumnName = "sup_id")
    private Supplier supplier;

    @Column(name = "quan", columnDefinition = "integer default 0")
    private Integer quantity;

    @Column(name = "date")
    @UpdateTimestamp
    private LocalDate updatedAt;

    public MerchInventory() {
    }

    public MerchInventory(Integer id, String name, Supplier supplier, Integer quantity) {
        this.id = id;
        this.name = name;
        this.supplier = supplier;
        this.quantity = quantity;
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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}