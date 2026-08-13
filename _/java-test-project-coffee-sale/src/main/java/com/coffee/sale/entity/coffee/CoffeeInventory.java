package com.coffee.sale.entity.coffee;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "cof_inventory")
public class CoffeeInventory {
    @EmbeddedId
    private CoffeeInventoryId id;

    @ManyToOne
    @JoinColumns(value = {
            @JoinColumn(name = "cof_name", referencedColumnName = "cof_name", insertable = false, updatable = false),
            @JoinColumn(name = "sup_id", referencedColumnName = "sup_id", insertable = false, updatable = false)
    })
    private Coffee coffee;

    @Column(name = "quan", columnDefinition = "integer default 0")
    private Integer quantity;

    @Column(name = "date_val")
    @UpdateTimestamp
    private LocalDate updatedAt;

    public CoffeeInventory() {
    }

    public CoffeeInventory(CoffeeInventoryId id, Coffee coffee, Integer quantity) {
        this.id = id;
        this.coffee = coffee;
        this.quantity = quantity;
    }

    public CoffeeInventoryId getId() {
        return id;
    }

    public void setId(CoffeeInventoryId id) {
        this.id = id;
    }

    public Coffee getCoffee() {
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
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

    @Override
    public String toString() {
        return "CoffeeInventory{" +
               "id=" + id +
               ", coffee=" + coffee +
               ", quantity=" + quantity +
               ", updatedAt=" + updatedAt +
               '}';
    }
}