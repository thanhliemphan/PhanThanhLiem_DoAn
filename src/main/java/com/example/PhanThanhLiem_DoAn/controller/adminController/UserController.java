package com.example.PhanThanhLiem_DoAn.controller.adminController;

import com.example.PhanThanhLiem_DoAn.dto.ProductDto;
import com.example.PhanThanhLiem_DoAn.model.User;
import com.example.PhanThanhLiem_DoAn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "admin/customers",method = RequestMethod.GET)
    public String customerPage(@RequestParam(value = "pageNo",required = false) Integer pageNo,
                               Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        if (pageNo == null) pageNo=0;
        Page<User> users = userService.pageProducts(pageNo,5);
        model.addAttribute("title","Manage Customer");
        model.addAttribute("size",users.getSize());
        model.addAttribute("totalPages",users.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("users",users);
        return "admin/customers";
    }
    @RequestMapping(value = "admin/search-customer",method = RequestMethod.GET)
    public String searchProducts(@RequestParam(value = "pageNo",required = false) Integer pageNo,
                                 @Param("keyword") String keyword,
                                 Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        if (pageNo == null) pageNo=0;
        Page<User> users = userService.searchCustomer(pageNo,keyword);
        model.addAttribute("title", "Search Customers");
        model.addAttribute("users", users);
        model.addAttribute("size", users.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("keyword",keyword);
        return "admin/customers";
    }
    @RequestMapping(value = "/enable-customer/{id}",method = RequestMethod.GET)
    public String enableProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        try {
            userService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enable successfully!");
        }catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to enabled");
        }
        return "redirect:/admin/customers";
    }
    @RequestMapping(value = "/delete-customer/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable("id") Long id,RedirectAttributes redirectAttributes){
        try {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Disable successfully");
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Faile to disable");
        }
        return "redirect:/admin/customers";
    }
}
