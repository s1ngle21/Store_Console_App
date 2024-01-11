package view;

import model.entity.Admin;
import model.entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminView {

    public static void displayMenu() {
        System.out.println("Admin Menu\n" +
                "1. Manage catalog\n" +
                "2. Display products\n" +
                "3. Exit");
    }

    public static void displayManageCatalogMenu() {
        System.out.println("Manage Catalog Menu\n" +
                "1. Add product\n" +
                "2. Delete product\n" +
                "3. Go back");
    }

    public static void displayProducts(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        for (Product p : products) {
                sb.append(p.getId() + ") ")
                        .append( "Name: ").append(p.getName() + "; ")
                        .append("Category: ").append(p.getCategory() + "; ")
                        .append("Price: ").append(p.getPrice() + "\n");
        }
        System.out.println(sb.toString());
    }
}
