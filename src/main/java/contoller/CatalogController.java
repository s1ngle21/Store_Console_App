package contoller;


import exceptions.NoProductsWereFoundException;
import exceptions.NoSuchCategoryException;
import exceptions.WrongInputException;
import helper.UIHelper;
import helper.ValidationHelper;
import model.entity.Product;
import model.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.math.BigDecimal;
import java.util.*;


public class CatalogController {
    private ProductService productService = new ProductService();
    private final Scanner SCAN = new Scanner(System.in);
    private Map<Integer, String> productMap = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void displayCatalogInNaturalOrder() {
        parseProducts(productService.getProducts());
    }

    public void displayCatalogSortedByAlphabetAscendingOrder() {
        List<Product> sortedProducts = productService.sortByNameFromAToZ();
        parseProducts(sortedProducts);
    }


    public void displayCatalogByPriceHighFirst() {
        List<Product> sortedProducts = productService.sortByPriceInAscendingOrder();
        parseProducts(sortedProducts);
    }

    public void displayCatalogByNewestFirst() {
        List<Product> sortedProducts = productService.sortByNewestFirst();
        parseProducts(sortedProducts);
    }

    public void displayCatalogByCategory() {
        List<Product> ownerProducts = productService.getProducts();
        StringBuilder sb = new StringBuilder();
        List<String> categories = new ArrayList<>();
        Map<Integer, String> categoryMap = new HashMap<>();
        int cnt = 1;
        sb.append("Chose from category\n");
        for (Product p : ownerProducts) {
            if (!categories.contains(p.getCategory())) {
                sb.append(cnt + ") ")
                        .append(p.getCategory() + "\n");
                categories.add(p.getCategory());
                categoryMap.put(cnt++, p.getCategory());
            }
        }
        sb.append("\nEnter category number: ");
        System.out.println(sb.toString());

        try {
            String categoryNumberStr = SCAN.nextLine();
            ValidationHelper.checkInputStringForNumbers(categoryNumberStr);
            Integer categoryNumber = Integer.parseInt(categoryNumberStr);

            String categoryName = categoryMap.get(categoryNumber);
            List<Product> productsFilteredByCategory = productService.filterByCategory(categoryName);

            if (categoryName == null) {
                UIHelper.sleep(1, "There is no such category, please try again!");
                throw new NoSuchCategoryException("Category was not found!\nInput: " + categoryNumberStr);
            }
            parseProducts(productsFilteredByCategory);
        } catch (WrongInputException | NoSuchCategoryException e) {
            logger.error(e.getMessage());
            displayCatalogByCategory();
        }
    }

    public void displayCatalogByPriceRange() {
        try {
            System.out.println("Enter min price: ");
            String minPriceStr = SCAN.nextLine();


            System.out.println("Enter max price: ");
            String maxPriceStr = SCAN.nextLine();

            ValidationHelper.checkInputStringForNumbers(minPriceStr, maxPriceStr);

            BigDecimal minPrice = BigDecimal.valueOf(Long.valueOf(minPriceStr));
            BigDecimal maxPrice = BigDecimal.valueOf(Long.valueOf(maxPriceStr));

            if (maxPrice.compareTo(BigDecimal.ZERO) == -1 || minPrice.compareTo(BigDecimal.ZERO) == -1) {
                UIHelper.sleep(1, "\nMin price and max price can not be less than 0, please try again!\n");
                throw new WrongInputException("Min price and max price can not be less than 0!" +
                        "\nInput[MinPrice: " + minPrice + "\nMaxPrice: " + maxPrice + "]");
            }

            List<Product> sortedProducts = productService.filterByPriceRange(minPrice, maxPrice);
            if (sortedProducts.isEmpty()) {
                UIHelper.sleep(1, "\nNo product by this price range were found, please try again\n");
                throw  new NoProductsWereFoundException("No product by this price range were found" +
                        "\nInput[MinPrice: " + minPrice + "\nMaxPrice: " + maxPrice + "]");
            }

            parseProducts(sortedProducts);

        } catch (WrongInputException | NoProductsWereFoundException e) {
            logger.error(e.getMessage());
            displayCatalogByPriceRange();
        }
    }

    private void parseProducts(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        int cnt = 1;
        for (Product p : products) {
            sb.append(cnt + ") ")
                    .append("Name: ").append(p.getName() + "; ")
                    .append("Category: ").append(p.getCategory() + "; ")
                    .append("Price: ").append(p.getPrice() + "; ")
                    .append("\n");
            productMap.put(cnt++, p.getName());
        }
        System.out.println(sb.toString());
    }

    public Map<Integer, String> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<Integer, String> productMap) {
        this.productMap = productMap;
    }
}
