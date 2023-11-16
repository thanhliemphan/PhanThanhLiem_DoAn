package com.example.PhanThanhLiem_DoAn.service;

import com.example.PhanThanhLiem_DoAn.dto.ProductDto;
import com.example.PhanThanhLiem_DoAn.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
//    List<ProductDto> findAll();
    List<ProductDto> products();
    List<ProductDto> allProduct();
    Product save(MultipartFile imageProduct, ProductDto productDto);
    Product update(MultipartFile imageProduct,ProductDto productDto);
    void deleteById(Long id);
    void enableById(Long id);
    ProductDto getById(Long id);
    Page<ProductDto> pageProducts(int pageNo,int pageSize,String sort);

    Page<ProductDto> searchProducts(int pageNo,String keyword,String sort);

    Page<ProductDto> pageAllProducts(int pageNo, int pageSize);

    Product getProductById(Long id);
    List<Product> getProductsInCategory(Long categoryId);

    void saveProduct(Product product);
}
