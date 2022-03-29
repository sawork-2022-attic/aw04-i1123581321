package com.example.webpos.model.entity;

import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

//@Entity
//@Table(name = "product")
public class Product implements Serializable {
//    @Id
    private String id;

//    @Column(name = "name", nullable = false)
    private String name;

//    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    @Column(name = "image", nullable = false)
    private String image;

    Product() {
    }

    public Product(String id, String name, BigDecimal price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price) && Objects.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, image);
    }
}
