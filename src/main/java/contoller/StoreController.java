package contoller;

import exceptions.InvalidCredentialsException;
import exceptions.WrongInputException;
import helper.UIHelper;
import helper.ValidationHelper;
import model.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.StoreView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StoreController {
    private static final Scanner SCAN = new Scanner(System.in);
    private static List<Customer> CUSTOMERS_LIST = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    public static void openShop() {

        CUSTOMERS_LIST.add(new Customer("Vanya", "Sambor", "vs", "12"));

        while (true) {
            try {
                StoreView.displayRegMenu();

                String input = SCAN.nextLine();
                ValidationHelper.checkInputStringForNumbers(input);
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1 -> login();
                    case 2 -> register();
                    case 3 -> {
                        System.exit(0);
                    }
                    default -> {
                        UIHelper.sleep(1, "Please enter from 1-3 only!");
                        throw new WrongInputException("Wrong input in 'Register Menu'!\n But was: " + choice);
                    }
                }
            } catch (WrongInputException e) {
                LOGGER.error(e.getMessage());
                openShop();
            }
        }
    }

    private static void login() {
        try {
            System.out.print("\nEnter your username: ");
            String username = SCAN.nextLine();

            System.out.print("Enter your password: ");
            String password = SCAN.nextLine();

            ValidationHelper.checkIfInputIsEmpty(username, password);

            if (username.equals("admin") && password.equals("123")) {
                UIHelper.loginSuccess();

                AdminController adminController = new AdminController();
                adminController.chooseFromMenu();
                return;
            }

            for (Customer customer : CUSTOMERS_LIST) {
                if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                    UIHelper.loginSuccess();

                    CustomerController customerController = new CustomerController(customer);
                    customerController.chooseFromMenu();
                    return;
                }
            }
            UIHelper.sleep(1, "No account was found!");
            throw new InvalidCredentialsException("Invalid username or password!");
        } catch (InvalidCredentialsException | WrongInputException e) {
            LOGGER.error(e.getMessage());
            login();
        }
    }


    private static void register() {
        try {
            System.out.print("\nEnter your first name: ");
            String firstName = SCAN.nextLine();

            System.out.print("Enter your last name: ");
            String lastName = SCAN.nextLine();

            System.out.print("Enter your username: ");
            String username = SCAN.nextLine();

            System.out.print("Enter your password: ");
            String password = SCAN.nextLine();

            ValidationHelper.checkIfInputIsEmpty(firstName, lastName, username, password);

            UIHelper.sleep(1, "Registration success!");
            CUSTOMERS_LIST.add(new Customer(firstName, lastName, username, password));
        } catch (WrongInputException e) {
            LOGGER.error(e.getMessage());
            register();
        }
    }
}

