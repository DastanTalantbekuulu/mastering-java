package com.mastering.concurrency.projects.coffee_shop;

/**
 * Food is what is prepared by Cooks, and ordered by Customers.  Food
 * is defined by its name, and the amount of time it takes to prepare
 * by Machine.  It is an immutable class.
 */
public record Food(String name, int cookTimeMS) {

    public String toString() {
        return name;
    }
}