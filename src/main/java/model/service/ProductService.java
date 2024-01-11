package model.service;

import model.entity.Product;
import model.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }
    public Product getByName(String name) {
        return productRepository.getByName(name);
    }

    public Product getById(Long id) {
       return productRepository.getById(id);
    }


    public List<Product> getProducts() {
        return productRepository.getProductsFromDataBase();
    }



    public List<Product> sortByNameFromAToZ() {
        return getProducts().stream()
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }


    public List<Product> sortByPriceInAscendingOrder() {
        return getProducts().stream()
                .sorted(Comparator.comparing(Product::getPrice))
                .collect(Collectors.toList());
    }


    public List<Product> sortByNewestFirst() {
        return getProducts().stream().
                sorted(Comparator.comparing(Product::isNew).reversed())
                .collect(Collectors.toList());
    }



    public List<Product> filterByCategory(String category) {
        return getProducts().stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toList());
    }


    public List<Product> filterByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return getProducts().stream()
                .filter(p -> p.getPrice().compareTo(minPrice) > 0 && p.getPrice().compareTo(maxPrice) < 0).collect(Collectors.toList());
    }

    public Long getLastProductId() {
        return productRepository.getLastProductId();
    }

    public void add(Product product) {
        productRepository.add(product);
    }

    public void delete(Long id) {
        productRepository.delete(id);
    }
}
