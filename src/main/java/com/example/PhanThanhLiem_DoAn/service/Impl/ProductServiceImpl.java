package com.example.PhanThanhLiem_DoAn.service.Impl;

import com.example.PhanThanhLiem_DoAn.dto.ProductDto;
import com.example.PhanThanhLiem_DoAn.model.Product;
import com.example.PhanThanhLiem_DoAn.repository.ProductRepository;
import com.example.PhanThanhLiem_DoAn.service.ProductService;
import com.example.PhanThanhLiem_DoAn.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ImageUpload imageUpload;
    @Override
    public List<ProductDto> products() {
        return transfer(productRepository.getAllProduct());
    }

    @Override
    public List<ProductDto> allProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = transfer(products);
        return productDtos;
    }
    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        Product product = new Product();
        try {
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                imageUpload.uploadFile(imageProduct);
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setCurrentQuantity(productDto.getCurrentQuantity());
            product.setCostPrice(productDto.getCostPrice());
            product.setSalePrice(productDto.getSalePrice());
            product.setCategory(productDto.getCategory());
            product.set_deleted(false);
            product.set_activated(true);
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = productRepository.findById(productDto.getId()).orElseThrow();
            if (imageProduct == null) {
                productUpdate.setImage(productUpdate.getImage());
            } else {
                if (imageUpload.checkExist(imageProduct)) {
                    productUpdate.setImage(productUpdate.getImage());
                } else {
                    imageUpload.uploadFile(imageProduct);
                    productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }
            }
            productUpdate.setName(productDto.getName());
            productUpdate.setCategory(productDto.getCategory());
            productUpdate.setId(productUpdate.getId());
            productUpdate.setDescription(productDto.getDescription());
            productUpdate.setCostPrice(productDto.getCostPrice());
            productUpdate.setSalePrice(productDto.getSalePrice());
            productUpdate.setCurrentQuantity(productDto.getCurrentQuantity());
            return productRepository.save(productUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.set_activated(false);
        product.set_deleted(true);
        productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.set_activated(true);
        product.set_deleted(false);
        productRepository.save(product);
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setPrice(product.getCostPrice()*(1-(product.getSalePrice()/100)));
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImage());
        productDto.setActivated(product.is_activated());
        productDto.setDeleted(product.is_deleted());
        return productDto;
    }

    @Override
    public Page<ProductDto> pageProducts(int pageNo,int pageSize,String sort) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        if (sort.equals("DESC")){
            List<ProductDto> products = transfer(productRepository.getAllProductOrderByDesc());
            Page<ProductDto> productPages = toPage(products,pageable);
            return productPages;
        }
            List<ProductDto> products = transfer(productRepository.getAllProductOrderByDAsc());
            Page<ProductDto> productPages = toPage(products,pageable);
        return productPages;
    }
    private Page toPage(List<ProductDto> list,Pageable pageable){
        if (pageable.getOffset() >= list.size()){
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (pageable.getOffset() + pageable.getPageSize() > list.size()) ?
                list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex,endIndex);
        return new PageImpl(subList,pageable, list.size());
    }
    @Override
    public Page<ProductDto> searchProducts(int pageNo,String keyword,String sort) {
        Pageable pageable = PageRequest.of(pageNo,8);
        if (sort.equalsIgnoreCase("DESC")){
            List<ProductDto> productDtos = transfer(productRepository.searchProductsListDesc(keyword));
            Page<ProductDto> products = toPage(productDtos, pageable);
            return products;
        }
        List<ProductDto> productDtos = transfer(productRepository.searchProductsListAsc(keyword));
        Page<ProductDto> products = toPage(productDtos, pageable);
        return products;
    }
    @Override
    public Page<ProductDto> pageAllProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,8);
        List<ProductDto> productDtos = transfer(productRepository.findAll());
        Page<ProductDto> products = toPage(productDtos, pageable);
        return products;
    }

    private List<ProductDto> transfer(List<Product> products){
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product: products){
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setPrice(product.getCostPrice()*(1-(product.getSalePrice()/100)));
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCategory(product.getCategory());
            productDto.setImage(product.getImage());
            productDto.setActivated(product.is_activated());
            productDto.setDeleted(product.is_deleted());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Product> getProductsInCategory(Long categoryId) {
        return productRepository.getProductsInCategory(categoryId);
    }


    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

}
