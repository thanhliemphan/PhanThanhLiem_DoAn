package com.example.PhanThanhLiem_DoAn.controller.adminController;

import com.example.PhanThanhLiem_DoAn.dto.ProductDto;
import com.example.PhanThanhLiem_DoAn.model.Category;
import com.example.PhanThanhLiem_DoAn.service.CategoryService;
import com.example.PhanThanhLiem_DoAn.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/admin/", method = RequestMethod.GET)
    public String indexAdmin(){
        return "admin/index";
    }

    @RequestMapping(value = "admin/products", method = RequestMethod.GET)
    public String product(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("products", productDtoList);
        model.addAttribute("size", productDtoList.size());
        return "admin/products";
    }
    @RequestMapping(value = "admin/products/{pageNo}",method = RequestMethod.GET)
    public String productsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.pageProducts(pageNo);
        model.addAttribute("title","Manage Product");
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("products",products);
        return "admin/products";
    }
    @RequestMapping(value = "admin/products/search-result/{pageNo}",method = RequestMethod.GET)
    public String searchProducts(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.searchProducts(pageNo,keyword);
        model.addAttribute("title", "Search Products");
        model.addAttribute("products", products);
        model.addAttribute("size", products.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("keyword","?keyword="+keyword);
        return "admin/result-products";
    }
    @RequestMapping(value = "/add-product", method = RequestMethod.GET)
    public String addProduct(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title", "Add Product");
        List<Category> categories = categoryService.findAllByActivatedTrue();
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductDto());
        return "admin/add-product";
    }
    @RequestMapping(value = "/save-product", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("productDto") ProductDto product,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes redirectAttributes) {
        try {
//            product.setPrice(product.getCostPrice()*(1-(product.getSalePrice()/100)));
            productService.save(imageProduct, product);
            redirectAttributes.addFlashAttribute("success", "Add new product successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new product!");
        }
        return "redirect:/admin/products/0";
    }
    @RequestMapping(value = "/update-product/{id}", method = RequestMethod.GET)
    public String updateProduct(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivatedTrue();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", categories);
        return "admin/update-product";
    }
    @RequestMapping(value = "/update-product/{id}", method = RequestMethod.POST)
    public String saveUpdateProduct(@ModelAttribute("productDto") ProductDto productDto,
                                    @RequestParam("imageProduct") MultipartFile imageProduct, RedirectAttributes redirectAttributes){
        try {
            productService.update(imageProduct, productDto);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/admin/products/0";
    }
    @RequestMapping(value = "/enable-product/{id}",method = RequestMethod.GET)
    public String enableProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try {
            productService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable successfully!");
        }catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to enabled");
        }
        return "redirect:/admin/products/0";
    }
    @RequestMapping(value = "/delete-product/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable("id") Long id,RedirectAttributes redirectAttributes){
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Delete successfully");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Faile to deleted");
        }
        return "redirect:/admin/products/0";
    }
}
