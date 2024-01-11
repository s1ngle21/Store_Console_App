package model.repository;

import helper.Utils;
import model.entity.Product;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private List<Product> products;
    private final String PRODUCTS_DATABASE_FILE_NAME = "products.txt";

    public ProductRepository() {
        this.products = getProductsFromDataBase();
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Product> getProductsFromDataBase() {
        List<Product> productsList = new ArrayList<>();
        File file = new File(PRODUCTS_DATABASE_FILE_NAME);
        String line;
        Long id = Long.valueOf(1);
        String name = "";
        BigDecimal price = BigDecimal.ZERO;
        String category = "";
        boolean isNew = false;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PRODUCTS_DATABASE_FILE_NAME))) {

            while ((line = bufferedReader.readLine()) != null) {
                line.trim();
                if (line.length() == 0) {
                    Product product = new Product(id, name, price, category, isNew);
                    productsList.add(product);
                    continue;
                }
                String[] lineSplit = line.split(":");
                String value = lineSplit[0].trim().replaceAll("\"", "");
                String key = lineSplit[1].trim().replaceAll("\"", "").replaceAll(",", "");

                switch (value) {
                    case "Id":
                        id = Long.valueOf(key);
                        break;
                    case "Name":
                        name = key;
                        break;
                    case "Price":
                        price = Utils.parseDecimal(key);
                        break;
                    case "Category":
                        category = key;
                        break;
                    case "isNew":
                        isNew = Boolean.valueOf(key);
                        break;
                }
            }
            Product product = new Product(id, name, price, category, isNew);
            productsList.add(product);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        return productsList;
    }

    public void add(Product product) {
        getProducts().add(product);
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(PRODUCTS_DATABASE_FILE_NAME, true)))) {
            printWriter.println("");
            printWriter.println("Id: " + product.getId());
            printWriter.println("Name: " + product.getName());
            printWriter.println("Price: " + product.getPrice());
            printWriter.println("Category: " + product.getCategory());
            printWriter.println("isNew: " + product.isNew());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Product getById(Long id) {
        List<Product> productsList = getProducts();
        return productsList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .get();
    }

    public void delete(Long id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_DATABASE_FILE_NAME))) {
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Id: " + id)) {
                    for (int i = 0; i < 5; i++) {
                        reader.readLine();
                    }
                } else {
                    sb.append(line).append("\n");
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_DATABASE_FILE_NAME));
            writer.write(sb.toString());
            writer.close();
            System.out.println("Product with Id " + id + " deleted successfully.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Product getByName(String name) {
        return getProducts().stream()
                .filter(p -> p.getName().equals(name))
                .findFirst().get();
    }

    public Long getLastProductId() {
        Product product = getProducts().get(getProducts().size() - 1);
        return product.getId();
    }
}
