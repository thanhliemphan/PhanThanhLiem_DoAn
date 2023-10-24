package com.example.PhanThanhLiem_DoAn.controller;

import com.example.PhanThanhLiem_DoAn.dto.CategoryDto;
import com.example.PhanThanhLiem_DoAn.dto.ProductDto;
import com.example.PhanThanhLiem_DoAn.model.*;
import com.example.PhanThanhLiem_DoAn.service.CategoryService;
import com.example.PhanThanhLiem_DoAn.service.ProductService;
import com.example.PhanThanhLiem_DoAn.service.ReviewService;
import com.example.PhanThanhLiem_DoAn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductHomeController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ReviewService reviewService;

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(Model model){
        List<Category> categories = categoryService.findAllByActivatedTrue();
        List<ProductDto> productDtos = productService.products();
        model.addAttribute("categories",categories);
        model.addAttribute("products",productDtos);
        return "index";
    }
    @RequestMapping(value = {"/", "index","/products"}, method = RequestMethod.GET)
    public String products(@RequestParam(value = "pageNo",required = false) Integer pageNo,
                           @RequestParam(value = "sort",required = false) String sort,
                           Model model){
        if (pageNo == null) pageNo=0;
        if (sort == null) sort = "DESC";
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> listViewProduct = productService.listViewProduct();
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("viewProduct", listViewProduct);
        model.addAttribute("product", new Product());
        Page<ProductDto> products = productService.pageProducts(pageNo,8,sort);
        model.addAttribute("title","Manage Product");
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("products",products);
        model.addAttribute("sort",sort);
        return "shop";
    }
    @RequestMapping(value = "/find-product/{id}", method = RequestMethod.GET)
    public String findProduct(@PathVariable("id") Long id, Model model){
        Product product = productService.getProductById(id);
//        Long categoryId = product.getCategory().getId();
        List<Review> reviews = product.getReviews();
        String avgRating = reviewService.getAvgRating(id);
        model.addAttribute("avgRating",avgRating);
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("review", new Review());
        return "product-detail";
    }
    @RequestMapping(value = "/products-in-category/{id}",method = RequestMethod.GET)
    public String getProductsInCatrgory(@PathVariable("id") Long categoryId, Model model){
        Category category = categoryService.findById(categoryId);
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getProductsInCategory(categoryId);
        model.addAttribute("categories", categories);
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        return "products-in-category";
    }
//    @RequestMapping(value = "high-price", method = RequestMethod.GET)
//    public String filterHighPrice(Model model){
//        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
//        List<ProductDto> products = productService.filterHighPrice();
//        List<ProductDto> listView = productService.listViewProducts();
//        model.addAttribute("productViews", listView);
//        model.addAttribute("categories", categoryDtoList);
//        model.addAttribute("products", products);
//        return "shop";
//    }
//    @RequestMapping(value = "low-price", method = RequestMethod.GET)
//    public String filterLowPrice(Model model){
//        List<ProductDto> listView = productService.listViewProducts();
//        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
//        List<ProductDto> products = productService.filterLowPrice();
//        model.addAttribute("productViews", listView);
//        model.addAttribute("categories", categoryDtoList);
//        model.addAttribute("products", products);
//        return "shop";
//    }
    @GetMapping(value = "/search-product")
    public String searchProducts(@Param("keyword") String keyword,
                                 @RequestParam(value = "pageNo",required = false) Integer pageNo,
                                 @RequestParam(value = "sort",required = false) String sort,
                                 Model model){
        if (sort == null) sort = "DESC";
        if (pageNo == null) pageNo=0;
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> listViewProduct = productService.listViewProduct();
        Page<ProductDto> products = productService.searchProducts(pageNo,keyword,sort);
        model.addAttribute("title", "Search Products");
        model.addAttribute("page", "Result Search");
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryDtoList);
        model.addAttribute("viewProduct", listViewProduct);
        model.addAttribute("product", new Product());
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("keyword",keyword);
        model.addAttribute("sort",sort);
        return "shop";
    }
    @PostMapping(value = "/product/sendReview")
    public String sendReview(@ModelAttribute("review")Review review,
                             @RequestParam("id") Long productId,
                             Principal principal){
        ProductDto productDto = productService.getById(productId);
        if (principal == null){
            return "redirect:/login";
        }else {
            String username = principal.getName();
            reviewService.addNewReview(review, productDto, username);
        }
        return "redirect:/find-product/"+productId;
    }

}
