package helper;


import exceptions.WrongInputException;
import model.entity.Customer;
import model.entity.Product;

import java.util.List;

public class ValidationHelper {

    public static void checkInputStringForNumbers(String... params) {
        for (String str : params) {
            if (str.isEmpty()) {
                UIHelper.sleep(1, "Please enter value!");
                throw new WrongInputException("Input string is empty: " + str);
            }
            if (str.matches("[A-Za-z]*")) {
                UIHelper.sleep(1, "Please enter only numbers!");
                throw new WrongInputException("Wrong input: " + str);
            }
        }
    }

    public static void checkInputString(String... params) {
        for (String str : params) {
            if (str.isEmpty()) {
                UIHelper.sleep(1, "Please enter value!");
                throw new WrongInputException("Input string is empty: " + str);
            }
            if (str.matches("[1-9]*")) {
                UIHelper.sleep(1, "Please enter only letters!");
                throw new WrongInputException("Wrong input: " + str);
            }
        }
    }

    public static void checkIfInputIsEmpty(String... params) {
        for (String str : params) {
            if (str == null || str.trim().isEmpty()) {
                UIHelper.sleep(1, "Please enter value!");
                throw new WrongInputException("Input string is empty: " + str);
            }
        }
    }

    public static boolean isCartEmpty(List<Product> products) {
        boolean result = false;

        if (products.size() == 0) {
            UIHelper.sleep(1, "Your cart is empty!");
            result = true;
        }
        return result;
    }
}
