package com.example.webpos.model.repository;


import com.example.webpos.model.entity.Product;


import java.util.Optional;


public interface ProductRepository{

    Optional<Product> findById(String s);

    Iterable<Product> findAll();
}
