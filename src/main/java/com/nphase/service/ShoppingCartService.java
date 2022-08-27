package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts()
                .stream()
                .map(product -> product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal calculateTotalWithDiscount(ShoppingCart shoppingCart){
       return shoppingCart.getProducts()
                .stream()
                .map(product -> {
                    if(product.getQuantity() > 3){
                       BigDecimal currentTotal = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
                       BigDecimal discountValue = product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()))
                               .multiply(new BigDecimal(10)).divide(BigDecimal.valueOf(100));
                       return currentTotal.subtract(discountValue);

                    }else {
                      return product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity()));
                    }
                }).reduce(BigDecimal::add)
               .orElse(BigDecimal.ZERO);
    }
    


    public BigDecimal calculateTotalWithDiscountAndCategory(ShoppingCart shoppingCart){
        Map<String, List<Product>> products = getListOfProducts(shoppingCart);
        return products.values()
                .stream()
                .map(this::categoryTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private BigDecimal categoryTotal(List<Product> products) {
        BigDecimal sum = products.stream()
                .map(product -> product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int quantity = products.stream()
                .mapToInt(Product::getQuantity)
                .sum();

        if (quantity > 3) {
            sum = sum.multiply(new BigDecimal("0.9"));
        }

        return sum;
    }

    private Map<String, List<Product>> getListOfProducts(ShoppingCart shoppingCart) {
        Map<String, List<Product>> products = shoppingCart.getProducts()
                .stream()
                .collect(Collectors.groupingBy(Product::getCategory));
        return products;
    }


   public BigDecimal calculateWithConfigurable(ShoppingCart shoppingCart){
       Map<String, List<Product>> products = getListOfProducts(shoppingCart);
       return products.values()
               .stream()
               .map(this::categoryTotalWithConfigurable)
               .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private BigDecimal categoryTotalWithConfigurable(List<Product> products) {
        BigDecimal sum = products.stream()
                .map(product -> product.getPricePerUnit().setScale(2, BigDecimal.ROUND_HALF_EVEN).multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int quantity = products.stream()
                .mapToInt(Product::getQuantity)
                .sum();

        if (quantity > 3) {
               products.stream()
                    .map(product -> sum.multiply(BigDecimal.valueOf(1-product.getDiscountPercentage())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            //sum = sum.multiply(BigDecimal.valueOf(1-product.getDiscountPercentage()));
        }

        return sum;
    }
}
