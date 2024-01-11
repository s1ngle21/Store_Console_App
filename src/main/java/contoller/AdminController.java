package contoller;


import exceptions.WrongInputException;
import helper.UIHelper;
import helper.ValidationHelper;
import model.entity.Product;
import model.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.AdminView;


import java.util.List;
import java.util.Scanner;

public class AdminController {
    private final Scanner SCAN = new Scanner(System.in);
    private ProductController productController;
    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public AdminController() {
        this.productService = new ProductService();
        this.productController = new ProductController();
    }

    public void chooseFromMenu() {
        try {
            while (true) {
                AdminView.displayMenu();

                String input = SCAN.nextLine();
                ValidationHelper.checkInputStringForNumbers(input);
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> manageCatalog();
                    case 2 -> {
                        AdminView.displayProducts(productService.getProducts());
                        chooseFromMenu();
                    }
                    case 3 -> StoreController.openShop();
                    default -> {
                        UIHelper.sleep(1, "Please enter from 1-3 only!");
                        throw new WrongInputException("Please enter from 1-3 only!\n But was: " + choice);
                    }
                }
            }
        } catch (WrongInputException e) {
            LOGGER.error(e.getMessage());
            chooseFromMenu();
        }
    }


    public void manageCatalog() {
        try {
            while (true) {
                AdminView.displayManageCatalogMenu();

                String input = SCAN.nextLine();
                ValidationHelper.checkInputStringForNumbers(input);
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> {
                        productController.addProduct();
                        manageCatalog();
                    }
                    case 2 -> {
                        productController.deleteProduct(productService.getProducts());
                        manageCatalog();
                    }
                    case 3 -> chooseFromMenu();
                    default -> {
                        UIHelper.sleep(1, "Please enter from 1-3 only!");
                        throw new WrongInputException("Wrong input in 'Manage Catalog Menu'!\nWas: " + choice);
                    }
                }
            }
        } catch (WrongInputException e) {
            LOGGER.error(e.getMessage());
            manageCatalog();
        }
    }

}
