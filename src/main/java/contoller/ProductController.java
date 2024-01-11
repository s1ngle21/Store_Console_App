package contoller;

import exceptions.WrongInputException;
import helper.UIHelper;
import helper.Utils;
import helper.ValidationHelper;
import model.entity.Product;
import model.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProductController {
    private Scanner scanner = new Scanner(System.in);
    private ProductService productService = new ProductService();
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void addProduct() {
        try {
            System.out.println("\nEnter product name: ");
            String name = scanner.nextLine();
            ValidationHelper.checkInputString(name);

            BigDecimal price = null;
            String priceStr = "";

            while (price == null) {
                System.out.println("Enter product price: ");
                priceStr = scanner.nextLine().replaceAll(",", ".");
                try {
                    price = Utils.parseDecimal(priceStr);
                } catch (ParseException e) {
                    UIHelper.sleep(1, "Please enter valid numbers!");
                }
            }

            String category = null;
            while (category == null) {
                System.out.println("Enter product category: ");
                category = scanner.nextLine();
                try {
                    ValidationHelper.checkInputString(category);
                } catch (WrongInputException e) {
                    category = null;
                }
            }

            Long id = productService.getLastProductId();


            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setCategory(category);
            product.setNew(true);
            product.setId(id + 1);

            productService.add(product);

            System.out.println("----------------------------\n" +
                    "Product has been added: \n" +
                    "Name: " + product.getName() + "\n" +
                    "Price: " + product.getPrice() + "\n" +
                    "Category: " + product.getCategory() +
                    "\n----------------------------");
        } catch (WrongInputException e) {
            addProduct();
            logger.error(e.getMessage());
        }
    }


    public void deleteProduct(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        Map<Long, String> productMap = new HashMap<>();
        long cnt = 1;
        for (Product p : products) {
            sb.append(p.getId() + ") ")
                    .append("Name: ").append(p.getName() + "; ")
                    .append("Category: ").append(p.getCategory() + "; ")
                    .append("Price: ").append(p.getPrice() + "\n");
            productMap.put(p.getId(), p.getName());
        }
        System.out.println(sb.toString());

        try {
            System.out.println("Enter product Id to delete: ");

            String idStr = scanner.nextLine();
            ValidationHelper.checkInputStringForNumbers(idStr);
            Long id = Long.parseLong(idStr);

            String productName = productMap.get(id);
            Product product = productService.getByName(productName);
            productService.delete(id);

            System.out.println("----------------------------\n" +
                    "Product has been deleted: \n" +
                    "Id: " + product.getId() + "\n" +
                    "Name: " + product.getName() + "\n" +
                    "Price: " + product.getPrice() + "\n" +
                    "Category: " + product.getCategory() + "\n" +
                    "isNew: " + product.isNew() +
                    "\n----------------------------");
        } catch (WrongInputException e) {
            deleteProduct(products);
            logger.error(e.getMessage());
        }
    }

}
