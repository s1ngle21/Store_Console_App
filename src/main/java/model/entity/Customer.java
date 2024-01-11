package model.entity;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<Product> cart = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public Customer(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%.1f", this.getFirstName(), this.getLastName(), this.getUsername(), this.getPassword());
    }

    public List<Product> getCart() {
        return cart;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
