package com.pesto.productservice.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "size")
    private String size;
    @Column(name = "color")
    private String color;
    @Column(name = "material")
    private String material;
    @Column(name = "price")
    private double price;
    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;
    @Column(name = "quantity_available")
    private int quantityAvailable;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(price, product.price) == 0 && quantityAvailable == product.quantityAvailable && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(size, product.size) && Objects.equals(color, product.color) && Objects.equals(material, product.material) && Objects.equals(category, product.category) && Objects.equals(version, product.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, size, color, material, price, category, quantityAvailable, version);
    }
}
