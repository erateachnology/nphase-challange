package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();


    @Test
    @DisplayName("Task 1 test")
    public void calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2,null, null),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1, null, null)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }

    @Test
    @DisplayName("Task 2 test")
    public void calculateTotalWithDiscountTest()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, null,null),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3, null, null)
        ));

        BigDecimal result = service.calculateTotalWithDiscount(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(33.00));
    }

    @Test
    @DisplayName("Task 3 test")
    public void calculateTotalWithDiscountAndCategoryTest()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, "drinks",null),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, "drinks",null),
                new Product("cheese", BigDecimal.valueOf(8), 2, "foods",null)
        ));

        BigDecimal result = service.calculateTotalWithDiscountAndCategory(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(31.84));
    }

    @Test
    @DisplayName("Task 4 test")
    public void calculateWithConfigurableTest()  {
        BigDecimal teaUnitPrice = new BigDecimal(5.3);
        BigDecimal coffeeUnitPrice = new BigDecimal(3.5);
        BigDecimal cheeseUnitPrice = new BigDecimal(8);
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", teaUnitPrice, 2, "drinks",0.1),
                new Product("Coffee", coffeeUnitPrice, 2, "drinks",0.2),
                new Product("cheese", cheeseUnitPrice, 2, "foods",0.3)
        ));

        BigDecimal result = service.calculateWithConfigurable(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(33.6));
    }

}