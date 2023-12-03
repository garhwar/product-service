package com.pesto.productservice.service;

import com.pesto.productservice.domain.entity.Category;
import com.pesto.productservice.domain.entity.Product;
import com.pesto.productservice.domain.repository.CategoryRepository;
import com.pesto.productservice.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
        try {
            Optional<Product> returnedProduct = productRepository.findById(productId);
            if (returnedProduct.isEmpty())
                return null;
            Product product = returnedProduct.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantityAvailable(updatedProduct.getQuantityAvailable());
            if (updatedProduct.getCategory() != null) {
                Category updatedCategory = updatedProduct.getCategory();
                Category existingCategory = product.getCategory();
                if (!updatedCategory.equals(existingCategory)) {
                    Optional<Category> returnedCategory = categoryRepository.findById(updatedCategory.getId());
                    if (returnedCategory.isEmpty())
                        throw new IllegalArgumentException("Category not found!");
                    Category category = returnedCategory.get();
                    product.setCategory(category);
                }
            }

            return productRepository.save(product);
        }
        catch (ObjectOptimisticLockingFailureException ex) {
            throw new IllegalStateException("The product has been updated by another user. Please retry.");
        }
    }

    public Page<Product> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    public Product getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElse(null);
    }

    public Page<Product> getProductsByIds(List<Long> productIds, PageRequest pageRequest) {
        return productRepository.findAllByIdIn(productIds, pageRequest);
    }
}
