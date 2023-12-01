package com.pesto.productservice.service;

import com.pesto.productservice.domain.entity.Category;
import com.pesto.productservice.domain.entity.Product;
import com.pesto.productservice.domain.repository.CategoryRepository;
import com.pesto.productservice.domain.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        /* Mocks */
        Product mockProduct = createProduct();
        when(productRepository.save(any())).thenReturn(mockProduct);

        /* Test */
        Product createdProduct = productService.createProduct(mockProduct);

        /* Verify */
        assertEquals(mockProduct, createdProduct);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void testUpdateProduct() {
        /* Mocks */
        Long productId = 1L;
        Product existingProduct = createProduct();
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        existingProduct.setName("New name");

        Category mockCategory = new Category();
        mockCategory.setName("New category");
        existingProduct.setCategory(mockCategory);

        when(categoryRepository.findById(any())).thenReturn(Optional.of(mockCategory));
        when(productRepository.save(any())).thenReturn(existingProduct);

        /* Test */
        Product result = productService.updateProduct(productId, existingProduct);

        /* Verify */
        assertEquals(existingProduct, result);
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void testGetAllProducts() {
        /* Mocks */
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Product> mockPage = mock(Page.class);
        when(productRepository.findAll(pageRequest)).thenReturn(mockPage);

        /* Test */
        Page<Product> result = productService.getAllProducts(pageRequest);

        /* Verify */
        Assertions.assertNotNull(result);
        assertEquals(mockPage, result);
        verify(productRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void testGetProductById() {
        /* Mock */
        Long productId = 1L;
        Product mockProduct = createProduct();
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        /* Test */
        Product result = productService.getProductById(productId);

        /* Verify */
        Assertions.assertNotNull(result);
        assertEquals(mockProduct, result);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductById_ProductNotFound() {
        /* Mock */
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        /* Test */
        Product result = productService.getProductById(productId);

        /* Verify */
        assertNull(result);
        verify(productRepository, times(1)).findById(productId);
    }

    private Product createProduct() {
        Product product = new Product();
        Category category = new Category();
        category.setName("Men's winter clothes");
        category.setDescription("Men's winter clothing");
        product.setName("Test_Product_Test");
        product.setColor("Red");
        product.setMaterial("Wool");
        product.setDescription("Woolly thermal wear");
        product.setCategory(category);
        return product;
    }

}