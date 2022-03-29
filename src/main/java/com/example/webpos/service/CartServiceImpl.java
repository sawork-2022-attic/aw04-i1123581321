package com.example.webpos.service;

import com.example.webpos.model.entity.Product;
import com.example.webpos.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService{
    private final ProductRepository repository;
    private final Cart cart;

    private final BigDecimal tax;
    private final BigDecimal discount;

    @Autowired
    CartServiceImpl(ProductRepository repository, Cart cart, @Value("${tax}") String tax, @Value("${discount}") String discount) {
        this.tax = new BigDecimal(tax);
        this.discount = new BigDecimal(discount);
        this.repository = repository;
        this.cart = cart;
    }

    @Override
    public BigDecimal tax() {
        return tax.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal discount() {
        return discount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal subtotal() {
        BigDecimal sum = new BigDecimal(0);
        for (var a : cart.entrySet()) {
            BigDecimal price = a.getKey().getPrice();
            BigDecimal count = BigDecimal.valueOf(a.getValue());
            sum = sum.add(price.multiply(count));
        }
        return sum.multiply(BigDecimal.valueOf(1).subtract(discount)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal total() {
        BigDecimal sub = subtotal();
        return sub.add(sub.multiply(tax)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public Iterable<Product> products() {
        return repository.findAll();
    }

    @Override
    public void resetCart() {
        cart.clear();
    }

    private Product checkArgument(String id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("product id does not exist"));
    }

    @Override
    public void addProduct(String id, int amount) {
        var product = checkArgument(id);
        cart.merge(product, amount, Integer::sum);
        if (cart.get(product) <= 0) {
            cart.remove(product);
        }
    }

    @Override
    public void removeProduct(String id) {
        var product = checkArgument(id);
        cart.remove(product);
    }

    @Override
    public void modifyCart(String id, int amount) {
        var product = checkArgument(id);
        cart.put(product, amount);
        if (cart.get(product) <= 0) {
            cart.remove(product);
        }
    }

    @Override
    public Set<Map.Entry<Product, Integer>> content() {
        return cart.entrySet();
    }
}
