package contoller;


import exceptions.WrongInputException;
import helper.UIHelper;
import helper.Utils;
import helper.ValidationHelper;
import model.entity.Customer;
import model.entity.Order;
import model.entity.Product;
import model.repository.ProductRepository;
import model.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.CustomerView;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CustomerController {
    private final Scanner SCAN = new Scanner(System.in);
    private ProductRepository productRepository = new ProductRepository();
    private CatalogController catalogController = new CatalogController();
    private ProductService productService = new ProductService();
    private CustomerView customerView = new CustomerView();
    private List<Product> customerCart;
    private List<Order> orders;
    private Customer customer;
    private Logger Logger = LoggerFactory.getLogger(getClass());

    public CustomerController(Customer customer) {
        this.customerCart = customer.getCart();
        this.orders = customer.getOrders();
        this.customer = customer;
    }

    public void chooseFromMenu() {
        try {
        while (true) {
            customerView.displayMenu();

            String input = SCAN.nextLine();
            ValidationHelper.checkInputStringForNumbers(input);
            int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> browseCatalog();
                    case 2 -> manageCart();
                    case 3 -> customerView.displayOrders(orders);
                    case 4 -> StoreController.openShop();
                    default -> {
                        UIHelper.sleep(1, "Please enter from 1-4 only!");
                        throw new WrongInputException("\nWrong input in 'Catalog Menu'!\nWas: " + choice);
                    }
                }
            }
        }catch (WrongInputException e) {
            Logger.error(e.getMessage());
            chooseFromMenu();
        }
    }


    public void browseCatalog() {
        try {
        while (true) {
            customerView.displayCatalogMenu();

            String input = SCAN.nextLine();
            ValidationHelper.checkInputStringForNumbers(input);
            int choice = Integer.parseInt(input);


                switch (choice) {
                    case 1 -> {
                        catalogController.displayCatalogInNaturalOrder();
                        choseProduct();
                    }
                    case 2 -> {
                        catalogController.displayCatalogSortedByAlphabetAscendingOrder();
                        choseProduct();
                    }
                    case 3 -> {
                        catalogController.displayCatalogByPriceHighFirst();
                        choseProduct();
                    }
                    case 4 -> {
                        catalogController.displayCatalogByNewestFirst();
                        choseProduct();
                    }
                    case 5 -> {
                        catalogController.displayCatalogByCategory();
                        choseProduct();
                    }
                    case 6 -> {
                        catalogController.displayCatalogByPriceRange();
                        choseProduct();
                    }
                    case 7 -> chooseFromMenu();
                    default -> {
                        UIHelper.sleep(1, "Please enter from 1-7 only!");
                        throw new WrongInputException("\nWrong input in 'Catalog Menu'!\nWas: " + choice);
                    }
                }
            }
        } catch (WrongInputException e) {
            Logger.error(e.getMessage());
            browseCatalog();
        }
    }

    private void manageCart() {
        try {
            while (true) {
                if (ValidationHelper.isCartEmpty(customerCart)) {
                    chooseFromMenu();
                }
                customerView.displayCart(customerCart);

                String input = SCAN.nextLine();
                ValidationHelper.checkInputStringForNumbers(input);
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> createOrder(customerCart, orders);
                    case 0 -> chooseFromMenu();
                    default -> {
                        UIHelper.sleep(1, "Please enter 0 or 1 only!");
                        throw new WrongInputException("\nWrong input in 'Cart Menu'!\nWas: " + choice);
                    }
                }
            }
        } catch (WrongInputException e) {
            Logger.error(e.getMessage());
            manageCart();
        }
    }


    private void createOrder(List<Product> customerCart, List<Order> orders) {
        Order order = new Order();
        order.setProducts(customerCart);
        orders.add(order);
        order.setValue(Utils.getCartValue(customerCart));
        System.out.println("Order has been placed \n");
        System.out.println("----------------------");
        chooseFromMenu();
    }


    private void choseProduct() {
        try {
            System.out.print("Enter product number: ");
            String productNumberStr = SCAN.nextLine();
            ValidationHelper.checkInputStringForNumbers(productNumberStr);
            Integer productNumber = Integer.valueOf(productNumberStr);

            String productName = catalogController.getProductMap().get(productNumber);
            Product chosenProduct = productService.getByName(productName);
            customerView.displayAddToCartMenu();

            String input = SCAN.nextLine();
            ValidationHelper.checkInputStringForNumbers(input);
            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1 -> customerCart.add(chosenProduct);
                case 0 -> browseCatalog();
                default -> {
                    UIHelper.sleep(1, "Please enter 0 or 1 only!");
                    choseProduct();
                    throw new WrongInputException("\nWrong input in 'Display Add To Cart Menu'!\nWas: " + choice);
                }
            }
        } catch (WrongInputException e) {
            Logger.error(e.getMessage());
            choseProduct();
        } catch (NoSuchElementException e) {
            UIHelper.sleep(1, "No such product in catalog");
            choseProduct();
            Logger.error("Product not found, wrong input");
        }
    }

}
