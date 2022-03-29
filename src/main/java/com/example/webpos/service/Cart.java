package com.example.webpos.service;

import com.example.webpos.model.entity.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Component
@SessionScope
public class Cart implements Serializable {
    private final Map<Product, Integer> cart = new HashMap<>();

    public void put(Product key, Integer value) {
        cart.put(key, value);
    }

    public Integer remove(Product key) {
        return cart.remove(key);
    }

    public Set<Map.Entry<Product, Integer>> entrySet() {
        return cart.entrySet();
    }

    public void clear() {
        cart.clear();
    }

    public Integer get(Product key) {
        return cart.get(key);
    }

    public Integer merge(Product key, Integer value, BiFunction<? super Integer, ? super Integer, ? extends Integer> remappingFunction) {
        return cart.merge(key, value, remappingFunction);
    }
}
