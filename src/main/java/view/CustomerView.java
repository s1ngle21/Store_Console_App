package view;

import contoller.CustomerController;
import helper.Utils;
import helper.ValidationHelper;
import model.entity.Order;
import model.entity.Product;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomerView {


    public void displayMenu() {
        System.out.println("Customer Menu\n" +
                "1. Catalog\n" +
                "2. Cart\n" +
                "3. Orders\n" +
                "4. Exit");
    }

    public void displayCatalogMenu() {
        System.out.println("""
                Catalog menu:
                1. Natural order
                2. Sort from a-z
                3. Sort by price in ascending order
                4. Sort by newest first
                5. Filter by category
                6. Filter by price range
                7. Go back to main menu
                """);
    }

    public void displayAddToCartMenu() {
        System.out.println("""
                \nDo you want to add to cart?
                1 -> Add to cart 
                0 -> Go back to catalog menu    
                """);
    }

    public void displayCart(List<Product> productsInCart) {
        StringBuilder sb = new StringBuilder();
        int cnt = 1;
        BigDecimal cartValue = Utils.getCartValue(productsInCart);

        for (Product p : productsInCart) {
            sb.append(cnt++ + ") ")
                    .append( "Name: ").append(p.getName() + "; ")
                    .append("Category: ").append(p.getCategory() + "; ")
                    .append("Price: ").append(p.getPrice() + "\n");
        }
        sb.append("-------------------------\n")
                .append("Cart value: " + cartValue).append("\n");
        System.out.println(sb.toString());
        System.out.println("------------------------");
        System.out.println("Press 1 to place order");
        System.out.println("Press 0 to go back");
    }

    public void displayOrders(List<Order> orders) {
        StringBuilder sb = new StringBuilder();
        int cnt = 1;
        if (orders.isEmpty()) {
            System.out.println("\n\nYou haven't placed any orders yet!\n\n");
        } else {
            for (Order o : orders) {
                sb.append(cnt++ + ") ").append("Order: \n");
                List<Product> products = o.getProducts();
                for (Product product : products) {
                    sb.append("Product Name: ").append(product.getName() + "; ")
                            .append("Price: ").append(product.getPrice() + "; ")
                            .append("Category: ").append(product.getCategory() + "; \n");
                }
                sb.append("--------------------------------------------------------")
                        .append("\nDate: ").append(o.getOrderDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss")))
                        .append("\nOrder value: " + o.getValue() + "\n");
            }
            System.out.println(sb.toString());
        }
    }
}
