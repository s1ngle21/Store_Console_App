package model.entity;

import model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    private ProductRepository productRepository = new ProductRepository();
    private List<Product> productList;
    private List<Customer> customerList;

    public Admin() {
        super("Vanya", "Sambor", "admin", "123");
        this.productList = productRepository.getProductsFromDataBase();
        this.customerList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
}
